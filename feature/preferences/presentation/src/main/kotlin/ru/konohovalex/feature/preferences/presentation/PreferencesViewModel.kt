package ru.konohovalex.feature.preferences.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.konohovalex.core.data.model.MergedOperationStatus2
import ru.konohovalex.core.data.model.OperationStatus
import ru.konohovalex.core.presentation.arch.effect.EffectPublisher
import ru.konohovalex.core.presentation.arch.effect.EffectPublisherDelegate
import ru.konohovalex.core.presentation.arch.event.EventHandler
import ru.konohovalex.core.presentation.arch.state.ScreenStateProvider
import ru.konohovalex.core.presentation.arch.state.ScreenStateProviderDelegate
import ru.konohovalex.core.utils.Mapper
import ru.konohovalex.core.utils.combineToMergedOperationStatus2
import ru.konohovalex.core.utils.safeCast
import ru.konohovalex.feature.preferences.domain.model.LanguageDomainModel
import ru.konohovalex.feature.preferences.domain.model.ThemeModeDomainModel
import ru.konohovalex.feature.preferences.domain.usecase.GetCurrentLanguageUseCase
import ru.konohovalex.feature.preferences.domain.usecase.GetCurrentThemeModeUseCase
import ru.konohovalex.feature.preferences.domain.usecase.UpdateCurrentLanguageUseCase
import ru.konohovalex.feature.preferences.domain.usecase.UpdateCurrentThemeModeUseCase
import ru.konohovalex.feature.preferences.presentation.model.LanguageUiModel
import ru.konohovalex.feature.preferences.presentation.model.PreferencesScreenEffect
import ru.konohovalex.feature.preferences.presentation.model.PreferencesScreenEvent
import ru.konohovalex.feature.preferences.presentation.model.PreferencesScreenState
import ru.konohovalex.feature.preferences.presentation.model.PreferencesUiModel
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
    EventHandler<PreferencesScreenEvent>,
    ScreenStateProvider<PreferencesScreenState> by ScreenStateProviderDelegate(PreferencesScreenState.Idle),
    EffectPublisher<PreferencesScreenEffect> by EffectPublisherDelegate() {
    override fun handle(event: PreferencesScreenEvent) = when (event) {
        is PreferencesScreenEvent.GetPreferences -> getPreferences()
        is PreferencesScreenEvent.UpdateCurrentLanguage -> updateCurrentLanguage(event.languageUiModel)
        is PreferencesScreenEvent.UpdateCurrentThemeMode -> updateCurrentThemeMode(event.themeModeUiModel)
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
        setScreenState(PreferencesScreenState.Loading)
    }

    private fun setDataState(
        currentLanguageDomainModel: LanguageDomainModel,
        currentThemeModeDomainModel: ThemeModeDomainModel,
    ) {
        val availableLanguages = LanguageUiModel.values().toList()
        val currentLanguageUiModel = languageDomainModelToLanguageUiModelMapper.invoke(currentLanguageDomainModel)

        val availableThemeModes = ThemeModeUiModel.values().toList()
        val currentThemeModeUiModel = themeModeDomainModelToThemeModeUiModelMapper.invoke(currentThemeModeDomainModel)

        setScreenState(
            screenState = PreferencesScreenState.Data(
                PreferencesUiModel(
                    availableLanguages = availableLanguages,
                    currentLanguageUiModel = currentLanguageUiModel,
                    availableThemeModes = availableThemeModes,
                    currentThemeModeUiModel = currentThemeModeUiModel,
                )
            ),
        )
    }

    private fun setErrorState(
        operationStatus: MergedOperationStatus2.Plain.Error<LanguageDomainModel, ThemeModeDomainModel>,
    ) {
        // tbd create real error handling
        setScreenState(PreferencesScreenState.Error(IllegalStateException()))
    }

    private fun updateCurrentLanguage(languageUiModel: LanguageUiModel) {
        val languageDomainModel = languageUiModelToLanguageDomainModelMapper.invoke(languageUiModel)
        updateCurrentLanguageUseCase.invoke(languageDomainModel)
            .onEach { operationStatus ->
                when (operationStatus) {
                    is OperationStatus.WithInputData.Pending -> {
                        publishEffect(PreferencesScreenEffect.EnableLanguageUpdates(value = false))
                    }
                    is OperationStatus.WithInputData.Processing -> {}
                    is OperationStatus.WithInputData.Completed -> {
                        publishEffect(PreferencesScreenEffect.EnableLanguageUpdates(value = true))

                        val updatedLanguageDomainModel = languageDomainModelToLanguageUiModelMapper.invoke(operationStatus.outputData)
                        withScreenState { preferencesScreenState ->
                            preferencesScreenState.safeCast<PreferencesScreenState.Data>()?.let {
                                setScreenState(
                                    it.copy(
                                        preferencesUiModel = it.preferencesUiModel.copy(
                                            currentLanguageUiModel = updatedLanguageDomainModel
                                        )
                                    )
                                )
                            }
                        }
                    }
                    is OperationStatus.WithInputData.Error -> {
                        publishEffect(PreferencesScreenEffect.EnableLanguageUpdates(value = true))
                        showErrorNotification(operationStatus)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun updateCurrentThemeMode(themeModeUiModel: ThemeModeUiModel) {
        val themeModeDomainModel = themeModeUiModelToThemeModeDomainModelMapper.invoke(themeModeUiModel)
        updateCurrentThemeModeUseCase.invoke(themeModeDomainModel)
            .onEach { operationStatus ->
                when (operationStatus) {
                    is OperationStatus.WithInputData.Pending -> {
                        publishEffect(PreferencesScreenEffect.EnableThemeModeUpdates(value = false))
                    }
                    is OperationStatus.WithInputData.Processing -> {}
                    is OperationStatus.WithInputData.Completed -> {
                        publishEffect(PreferencesScreenEffect.EnableThemeModeUpdates(value = true))

                        val updatedThemeModeDomainModel = themeModeDomainModelToThemeModeUiModelMapper.invoke(operationStatus.outputData)
                        withScreenState { preferencesScreenState ->
                            preferencesScreenState.safeCast<PreferencesScreenState.Data>()?.let {
                                setScreenState(
                                    it.copy(
                                        preferencesUiModel = it.preferencesUiModel.copy(
                                            currentThemeModeUiModel = updatedThemeModeDomainModel
                                        )
                                    )
                                )
                            }
                        }
                    }
                    is OperationStatus.WithInputData.Error -> {
                        publishEffect(PreferencesScreenEffect.EnableThemeModeUpdates(value = true))
                        showErrorNotification(operationStatus)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private inline fun <reified I, reified O> showErrorNotification(operationStatus: OperationStatus.WithInputData.Error<I, O>) {
        // tbd
    }
}
