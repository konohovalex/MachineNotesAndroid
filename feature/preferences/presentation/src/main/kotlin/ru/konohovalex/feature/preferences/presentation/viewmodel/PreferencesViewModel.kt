package ru.konohovalex.feature.preferences.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import ru.konohovalex.core.presentation.arch.viewevent.ViewEventHandler
import ru.konohovalex.core.presentation.arch.viewstate.ViewStateProvider
import ru.konohovalex.core.presentation.arch.viewstate.ViewStateProviderDelegate
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.core.utils.model.OperationStatus
import ru.konohovalex.feature.preferences.domain.model.LanguageDomainModel
import ru.konohovalex.feature.preferences.domain.model.PreferencesDomainModel
import ru.konohovalex.feature.preferences.domain.model.ThemeModeDomainModel
import ru.konohovalex.feature.preferences.domain.usecase.ObservePreferencesUseCase
import ru.konohovalex.feature.preferences.domain.usecase.UpdateLanguageUseCase
import ru.konohovalex.feature.preferences.domain.usecase.UpdateThemeModeUseCase
import ru.konohovalex.feature.preferences.presentation.model.LanguageUiModel
import ru.konohovalex.feature.preferences.presentation.model.PreferencesScreenViewEvent
import ru.konohovalex.feature.preferences.presentation.model.PreferencesUiModel
import ru.konohovalex.feature.preferences.presentation.model.PreferencesViewState
import ru.konohovalex.feature.preferences.presentation.model.ThemeModeUiModel
import javax.inject.Inject

@HiltViewModel
internal class PreferencesViewModel
@Inject constructor(
    private val observePreferencesUseCase: ObservePreferencesUseCase,
    private val updateLanguageUseCase: UpdateLanguageUseCase,
    private val updateThemeModeUseCase: UpdateThemeModeUseCase,
    private val languageDomainModelToLanguageUiModelMapper: Mapper<LanguageDomainModel, LanguageUiModel>,
    private val languageUiModelToLanguageDomainModelMapper: Mapper<LanguageUiModel, LanguageDomainModel>,
    private val themeModeDomainModelToThemeModeUiModelMapper: Mapper<ThemeModeDomainModel, ThemeModeUiModel>,
    private val themeModeUiModelToThemeModeDomainModelMapper: Mapper<ThemeModeUiModel, ThemeModeDomainModel>,
) : ViewModel(),
    ViewEventHandler<PreferencesScreenViewEvent>,
    ViewStateProvider<PreferencesViewState> by ViewStateProviderDelegate(PreferencesViewState.Idle) {
    private var observePreferencesJob: Job? = null
    private var updateLanguageJob: Job? = null
    private var updateThemeModeJob: Job? = null

    override fun handle(viewEvent: PreferencesScreenViewEvent) = when (viewEvent) {
        is PreferencesScreenViewEvent.GetPreferences -> getPreferences()
        is PreferencesScreenViewEvent.UpdateLanguage -> updateLanguage(viewEvent.languageUiModel)
        is PreferencesScreenViewEvent.UpdateThemeMode -> updateThemeMode(viewEvent.themeModeUiModel)
    }

    private fun getPreferences() {
        observePreferencesJob?.cancel()
        observePreferencesJob = viewModelScope.launch {
            observePreferencesUseCase.invoke()
                .onEach {
                    when (it) {
                        is OperationStatus.Plain.Pending -> setLoadingState()
                        is OperationStatus.Plain.Processing -> {}
                        is OperationStatus.Plain.Completed -> setDataState(it.outputData)
                        is OperationStatus.Plain.Error -> setErrorState(it.throwable)
                    }
                }
                .take(1)
                .collect()
        }
    }

    private fun setLoadingState() {
        setViewState(PreferencesViewState.Loading)
    }

    private fun setDataState(preferencesDomainModel: PreferencesDomainModel) {
        val availableLanguages = LanguageUiModel.values().toList()
        val currentLanguageUiModel =
            languageDomainModelToLanguageUiModelMapper.invoke(preferencesDomainModel.languageDomainModel)

        val availableThemeModes = ThemeModeUiModel.values().toList()
        val currentThemeModeUiModel =
            themeModeDomainModelToThemeModeUiModelMapper.invoke(preferencesDomainModel.themeModeDomainModel)

        setViewState(
            viewState = PreferencesViewState.Data(
                PreferencesUiModel(
                    availableLanguages = availableLanguages,
                    currentLanguageUiModel = currentLanguageUiModel,
                    languageActionsEnabled = true,
                    availableThemeModes = availableThemeModes,
                    currentThemeModeUiModel = currentThemeModeUiModel,
                    themeModeActionsEnabled = true,
                )
            ),
        )
    }

    private fun updateLanguage(languageUiModel: LanguageUiModel) {
        updateLanguageJob?.cancel()
        val languageDomainModel = languageUiModelToLanguageDomainModelMapper.invoke(languageUiModel)
        updateLanguageJob = updateLanguageUseCase.invoke(languageDomainModel)
            .onEach { operationStatus ->
                when (operationStatus) {
                    is OperationStatus.WithInputData.Pending -> disableLanguageUpdates()
                    is OperationStatus.WithInputData.Processing -> {}
                    is OperationStatus.WithInputData.Completed -> languageUpdated(operationStatus.outputData)
                    is OperationStatus.WithInputData.Error -> showLanguageUpdateErrorNotification(operationStatus.throwable)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun disableLanguageUpdates() {
        withViewState(PreferencesViewState.Data::class.java) {
            setViewState(
                it.copy(
                    preferencesUiModel = it.preferencesUiModel.copy(
                        languageActionsEnabled = false,
                    )
                )
            )
        }
    }

    private fun languageUpdated(languageDomainModel: LanguageDomainModel) {
        val updatedLanguageDomainModel = languageDomainModelToLanguageUiModelMapper.invoke(languageDomainModel)
        withViewState(PreferencesViewState.Data::class.java) {
            setViewState(
                it.copy(
                    preferencesUiModel = it.preferencesUiModel.copy(
                        currentLanguageUiModel = updatedLanguageDomainModel,
                        languageActionsEnabled = true,
                    )
                )
            )
        }
    }

    private fun showLanguageUpdateErrorNotification(throwable: Throwable) {
        withViewState(PreferencesViewState.Data::class.java) {
            setViewState(
                it.copy(
                    preferencesUiModel = it.preferencesUiModel.copy(
                        languageActionsEnabled = true,
                    ),
                    throwable = throwable,
                )
            )
        }
    }

    private fun updateThemeMode(themeModeUiModel: ThemeModeUiModel) {
        updateThemeModeJob?.cancel()
        val themeModeDomainModel = themeModeUiModelToThemeModeDomainModelMapper.invoke(themeModeUiModel)
        updateThemeModeJob = updateThemeModeUseCase.invoke(themeModeDomainModel)
            .onEach { operationStatus ->
                when (operationStatus) {
                    is OperationStatus.WithInputData.Pending -> disableThemeModeUpdates()
                    is OperationStatus.WithInputData.Processing -> {}
                    is OperationStatus.WithInputData.Completed -> themeModeUpdated(operationStatus.outputData)
                    is OperationStatus.WithInputData.Error -> showThemeModeUpdateUpdateErrorNotification(operationStatus.throwable)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun disableThemeModeUpdates() {
        withViewState(PreferencesViewState.Data::class.java) {
            setViewState(
                it.copy(
                    preferencesUiModel = it.preferencesUiModel.copy(
                        themeModeActionsEnabled = false,
                    )
                )
            )
        }
    }

    private fun themeModeUpdated(themeModeUiModel: ThemeModeDomainModel) {
        val updatedThemeModeDomainModel = themeModeDomainModelToThemeModeUiModelMapper.invoke(themeModeUiModel)
        withViewState(PreferencesViewState.Data::class.java) {
            setViewState(
                it.copy(
                    preferencesUiModel = it.preferencesUiModel.copy(
                        currentThemeModeUiModel = updatedThemeModeDomainModel,
                        themeModeActionsEnabled = true,
                    )
                )
            )
        }
    }

    private fun showThemeModeUpdateUpdateErrorNotification(throwable: Throwable) {
        withViewState(PreferencesViewState.Data::class.java) {
            setViewState(
                it.copy(
                    preferencesUiModel = it.preferencesUiModel.copy(
                        themeModeActionsEnabled = true,
                    ),
                    throwable = throwable,
                ),
            )
        }
    }

    private fun setErrorState(throwable: Throwable) {
        setViewState(PreferencesViewState.Error(throwable))
    }
}
