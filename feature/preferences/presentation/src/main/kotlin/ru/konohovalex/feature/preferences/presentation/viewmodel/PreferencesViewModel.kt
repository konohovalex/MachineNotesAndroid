package ru.konohovalex.feature.preferences.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.konohovalex.core.presentation.arch.viewevent.ViewEventHandler
import ru.konohovalex.core.presentation.arch.viewstate.ViewStateProvider
import ru.konohovalex.core.presentation.arch.viewstate.ViewStateProviderDelegate
import ru.konohovalex.core.ui.model.Position
import ru.konohovalex.core.ui.model.TextWrapper
import ru.konohovalex.core.ui.model.TumblerData
import ru.konohovalex.core.utils.extension.combineToMergedOperationStatus2
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.core.utils.model.MergedOperationStatus2
import ru.konohovalex.core.utils.model.OperationStatus
import ru.konohovalex.feature.preferences.domain.model.LanguageDomainModel
import ru.konohovalex.feature.preferences.domain.model.ThemeModeDomainModel
import ru.konohovalex.feature.preferences.domain.usecase.GetCurrentLanguageUseCase
import ru.konohovalex.feature.preferences.domain.usecase.GetCurrentThemeModeUseCase
import ru.konohovalex.feature.preferences.domain.usecase.UpdateCurrentLanguageUseCase
import ru.konohovalex.feature.preferences.domain.usecase.UpdateCurrentThemeModeUseCase
import ru.konohovalex.feature.preferences.presentation.R
import ru.konohovalex.feature.preferences.presentation.model.LanguageUiModel
import ru.konohovalex.feature.preferences.presentation.model.PreferencesScreenViewEvent
import ru.konohovalex.feature.preferences.presentation.model.PreferencesUiModel
import ru.konohovalex.feature.preferences.presentation.model.PreferencesViewState
import ru.konohovalex.feature.preferences.presentation.model.ThemeModeUiModel
import javax.inject.Inject

@HiltViewModel
internal class PreferencesViewModel
@Inject constructor(
    private val getCurrentLanguageUseCase: GetCurrentLanguageUseCase,
    private val updateCurrentLanguageUseCase: UpdateCurrentLanguageUseCase,
    private val getCurrentThemeModeUseCase: GetCurrentThemeModeUseCase,
    private val updateCurrentThemeModeUseCase: UpdateCurrentThemeModeUseCase,
    private val languageDomainModelToLanguageUiModelMapper: Mapper<LanguageDomainModel, LanguageUiModel>,
    private val languageUiModelToLanguageDomainModelMapper: Mapper<LanguageUiModel, LanguageDomainModel>,
    private val themeModeDomainModelToThemeModeUiModelMapper: Mapper<ThemeModeDomainModel, ThemeModeUiModel>,
    private val themeModeUiModelToThemeModeDomainModelMapper: Mapper<ThemeModeUiModel, ThemeModeDomainModel>,
) : ViewModel(),
    ViewEventHandler<PreferencesScreenViewEvent>,
    ViewStateProvider<PreferencesViewState> by ViewStateProviderDelegate(PreferencesViewState.Idle) {
    override fun handle(viewEvent: PreferencesScreenViewEvent) = when (viewEvent) {
        is PreferencesScreenViewEvent.GetPreferences -> getPreferences()
        is PreferencesScreenViewEvent.UpdateCurrentLanguage -> updateCurrentLanguage(viewEvent.languageUiModel)
        is PreferencesScreenViewEvent.UpdateCurrentThemeMode -> updateCurrentThemeMode(viewEvent.themeModeUiModel)
    }

    private fun getPreferences() {
        getCurrentLanguageUseCase.invoke()
            .combineToMergedOperationStatus2(getCurrentThemeModeUseCase.invoke())
            .onEach {
                when (it) {
                    is MergedOperationStatus2.Plain.Pending -> setLoadingState()
                    is MergedOperationStatus2.Plain.Processing -> {}
                    is MergedOperationStatus2.Plain.Completed -> setDataState(it.first.outputData, it.second.outputData)
                    is MergedOperationStatus2.Plain.Error -> setErrorState(it)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun setLoadingState() {
        setViewState(PreferencesViewState.Loading)
    }

    private fun setDataState(
        currentLanguageDomainModel: LanguageDomainModel,
        currentThemeModeDomainModel: ThemeModeDomainModel,
    ) {
        val availableLanguages = LanguageUiModel.values().toList()
        val currentLanguageUiModel = languageDomainModelToLanguageUiModelMapper.invoke(currentLanguageDomainModel)
        val languageTumblerData = TumblerData(
            titleTextWrapper = TextWrapper.StringResource(resourceId = R.string.theme_mode_tumbler_title),
            positions = availableLanguages.mapToTumblerPositions(),
        )

        val availableThemeModes = ThemeModeUiModel.values().toList()
        val currentThemeModeUiModel = themeModeDomainModelToThemeModeUiModelMapper.invoke(currentThemeModeDomainModel)
        val themeModeTumblerData = TumblerData(
            titleTextWrapper = TextWrapper.StringResource(resourceId = R.string.language_tumbler_title),
            infoTextWrapper = TextWrapper.StringResource(resourceId = R.string.language_tumbler_info),
            positions = availableThemeModes.mapToTumblerPositions(),
        )

        setViewState(
            viewState = PreferencesViewState.Data(
                PreferencesUiModel(
                    availableLanguagesTumblerData = languageTumblerData,
                    currentLanguageUiModel = currentLanguageUiModel,
                    languageActionsEnabled = true,
                    availableThemeModesTumblerData = themeModeTumblerData,
                    currentThemeModeUiModel = currentThemeModeUiModel,
                    themeModeActionsEnabled = true,
                )
            ),
        )
    }

    @JvmName("mapLanguageUiModelsToTumblerPositions")
    private fun List<LanguageUiModel>.mapToTumblerPositions() = map {
        Position.Text(
            data = it,
            textWrapper = it.textWrapper,
        )
    }

    @JvmName("mapThemeModeUiModelsToTumblerPositions")
    private fun List<ThemeModeUiModel>.mapToTumblerPositions() = map {
        Position.Image(
            data = it,
            imageWrapper = it.imageWrapper,
        )
    }

    private fun updateCurrentLanguage(languageUiModel: LanguageUiModel) {
        val languageDomainModel = languageUiModelToLanguageDomainModelMapper.invoke(languageUiModel)
        updateCurrentLanguageUseCase.invoke(languageDomainModel)
            .onEach { operationStatus ->
                when (operationStatus) {
                    is OperationStatus.WithInputData.Pending -> disableLanguageUpdates()
                    is OperationStatus.WithInputData.Processing -> {}
                    is OperationStatus.WithInputData.Completed -> languageUpdated(operationStatus.outputData)
                    is OperationStatus.WithInputData.Error -> showLanguageUpdateErrorNotification(operationStatus)
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

    private inline fun <reified I, reified O> showLanguageUpdateErrorNotification(
        operationStatus: OperationStatus.WithInputData.Error<I, O>,
    ) {
        // tbd
        withViewState(PreferencesViewState.Data::class.java) {
            setViewState(
                it.copy(
                    preferencesUiModel = it.preferencesUiModel.copy(
                        languageActionsEnabled = true,
                    )
                )
            )
        }
    }

    private fun updateCurrentThemeMode(themeModeUiModel: ThemeModeUiModel) {
        val themeModeDomainModel = themeModeUiModelToThemeModeDomainModelMapper.invoke(themeModeUiModel)
        updateCurrentThemeModeUseCase.invoke(themeModeDomainModel)
            .onEach { operationStatus ->
                when (operationStatus) {
                    is OperationStatus.WithInputData.Pending -> disableThemeModeUpdates()
                    is OperationStatus.WithInputData.Processing -> {}
                    is OperationStatus.WithInputData.Completed -> themeModeUpdated(operationStatus.outputData)
                    is OperationStatus.WithInputData.Error -> showThemeModeUpdateUpdateErrorNotification(operationStatus)
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

    private inline fun <reified I, reified O> showThemeModeUpdateUpdateErrorNotification(
        operationStatus: OperationStatus.WithInputData.Error<I, O>,
    ) {
        // tbd
        withViewState(PreferencesViewState.Data::class.java) {
            setViewState(
                it.copy(
                    preferencesUiModel = it.preferencesUiModel.copy(
                        themeModeActionsEnabled = true,
                    )
                )
            )
        }
    }

    private fun setErrorState(
        operationStatus: MergedOperationStatus2.Plain.Error<LanguageDomainModel, ThemeModeDomainModel>,
    ) {
        // tbd create real error handling
        setViewState(PreferencesViewState.Error(IllegalStateException()))
    }
}
