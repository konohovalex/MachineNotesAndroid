package ru.konohovalex.machinenotes.app.presentation.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.konohovalex.core.presentation.arch.viewevent.ViewEventHandler
import ru.konohovalex.core.presentation.arch.viewstate.ViewStateProvider
import ru.konohovalex.core.presentation.arch.viewstate.ViewStateProviderDelegate
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.core.utils.model.OperationStatus
import ru.konohovalex.feature.preferences.domain.model.LanguageDomainModel
import ru.konohovalex.feature.preferences.domain.model.ThemeModeDomainModel
import ru.konohovalex.feature.preferences.domain.usecase.ObservePreferencesUseCase
import ru.konohovalex.feature.preferences.presentation.model.LanguageUiModel
import ru.konohovalex.feature.preferences.presentation.model.ThemeModeUiModel
import ru.konohovalex.machinenotes.app.domain.usecase.GetIsFirstLaunchUseCase
import ru.konohovalex.machinenotes.app.domain.usecase.SetIsFirstLaunchUseCase
import ru.konohovalex.machinenotes.app.presentation.main.model.MainViewEvent
import ru.konohovalex.machinenotes.app.presentation.main.model.MainViewState
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel
@Inject constructor(
    private val observePreferencesUseCase: ObservePreferencesUseCase,
    private val getIsFirstLaunchUseCase: GetIsFirstLaunchUseCase,
    private val setIsFirstLaunchUseCase: SetIsFirstLaunchUseCase,
    private val languageDomainModelToLanguageUiModelMapper: Mapper<LanguageDomainModel, LanguageUiModel>,
    private val themeModeDomainModelToThemeModeUiModelMapper: Mapper<ThemeModeDomainModel, ThemeModeUiModel>,
) : ViewModel(),
    ViewEventHandler<MainViewEvent>,
    ViewStateProvider<MainViewState> by ViewStateProviderDelegate(MainViewState.Idle) {
    private var isFirstLaunch = true

    private var initJob: Job? = null
    private var observePreferencesJob: Job? = null
    private var setIsFirstLaunchJob: Job? = null

    override fun handle(viewEvent: MainViewEvent) {
        when (viewEvent) {
            is MainViewEvent.Init -> init()
            is MainViewEvent.FirstLaunchCompleted -> firstLaunchCompleted()
        }
    }

    private fun init() {
        initJob?.cancel()
        initJob = getIsFirstLaunchUseCase.invoke()
            .onEach { operationStatus ->
                when (operationStatus) {
                    is OperationStatus.Plain.Pending, is OperationStatus.Plain.Processing -> {}
                    is OperationStatus.Plain.Completed -> {
                        isFirstLaunch = operationStatus.outputData
                        observePreferences()
                    }
                    is OperationStatus.Plain.Error -> setErrorState(operationStatus.throwable)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun observePreferences() {
        observePreferencesJob?.cancel()
        observePreferencesJob = viewModelScope.launch {
            observePreferencesUseCase.invoke()
                .onEach {
                    when (it) {
                        is OperationStatus.Plain.Pending, is OperationStatus.Plain.Processing -> {}
                        is OperationStatus.Plain.Completed -> {
                            val languageUiModel = languageDomainModelToLanguageUiModelMapper.invoke(
                                it.outputData.languageDomainModel
                            )
                            val themeModeUiModel = themeModeDomainModelToThemeModeUiModelMapper.invoke(
                                it.outputData.themeModeDomainModel
                            )

                            setDataState(languageUiModel, themeModeUiModel)
                        }
                        is OperationStatus.Plain.Error -> setErrorState(it.throwable)
                    }
                }
                .collect()
        }
    }

    private fun firstLaunchCompleted() {
        withViewState(MainViewState.FirstLaunch::class.java) { viewState ->
            setIsFirstLaunchJob?.cancel()
            setIsFirstLaunchJob = setIsFirstLaunchUseCase.invoke(false)
                .onEach {
                    when (it) {
                        is OperationStatus.Plain.Pending -> {}
                        is OperationStatus.Plain.Processing -> {}
                        is OperationStatus.Plain.Completed -> {
                            isFirstLaunch = it.outputData
                            setDataState(
                                languageUiModel = viewState.languageUiModel,
                                themeModeUiModel = viewState.themeModeUiModel,
                            )
                        }
                        is OperationStatus.Plain.Error -> setErrorState(it.throwable)
                    }
                }
                .launchIn(viewModelScope)
        }
    }

    private fun setDataState(
        languageUiModel: LanguageUiModel?,
        themeModeUiModel: ThemeModeUiModel?,
    ) {
        setViewState(
            if (isFirstLaunch) MainViewState.FirstLaunch(languageUiModel, themeModeUiModel)
            else MainViewState.NotFirstLaunch(languageUiModel, themeModeUiModel)
        )
    }

    private fun setErrorState(throwable: Throwable) {
        viewState.value.let {
            setViewState(
                MainViewState.Error(
                    throwable = throwable,
                    languageUiModel = it?.languageUiModel,
                    themeModeUiModel = it?.themeModeUiModel,
                )
            )
        }
    }
}
