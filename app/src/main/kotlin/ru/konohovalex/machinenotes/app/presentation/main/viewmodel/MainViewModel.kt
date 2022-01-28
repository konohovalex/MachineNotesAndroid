package ru.konohovalex.machinenotes.app.presentation.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.konohovalex.core.presentation.arch.viewevent.ViewEventHandler
import ru.konohovalex.core.presentation.arch.viewstate.ViewStateProvider
import ru.konohovalex.core.presentation.arch.viewstate.ViewStateProviderDelegate
import ru.konohovalex.core.utils.model.OperationStatus
import ru.konohovalex.machinenotes.app.domain.usecase.GetIsFirstLaunchUseCase
import ru.konohovalex.machinenotes.app.domain.usecase.SetIsFirstLaunchUseCase
import ru.konohovalex.machinenotes.app.presentation.main.model.MainViewEvent
import ru.konohovalex.machinenotes.app.presentation.main.model.MainViewState
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel
@Inject constructor(
    private val getIsFirstLaunchUseCase: GetIsFirstLaunchUseCase,
    private val setIsFirstLaunchUseCase: SetIsFirstLaunchUseCase,
) : ViewModel(),
    ViewEventHandler<MainViewEvent>,
    ViewStateProvider<MainViewState> by ViewStateProviderDelegate(MainViewState.Idle) {
    override fun handle(viewEvent: MainViewEvent) {
        when (viewEvent) {
            is MainViewEvent.GetIsFirstLaunch -> getIsFirstLaunch()
            is MainViewEvent.FirstLaunchCompleted -> firstLaunchCompleted()
        }
    }

    private fun getIsFirstLaunch() {
        getIsFirstLaunchUseCase.invoke()
            .onEach {
                when (it) {
                    is OperationStatus.Plain.Pending -> {}
                    is OperationStatus.Plain.Processing -> {}
                    is OperationStatus.Plain.Completed -> setDateState(it.outputData)
                    is OperationStatus.Plain.Error -> setErrorState(it.throwable)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun firstLaunchCompleted() {
        setIsFirstLaunchUseCase.invoke(false)
            .onEach {
                when (it) {
                    is OperationStatus.Plain.Pending -> {}
                    is OperationStatus.Plain.Processing -> {}
                    is OperationStatus.Plain.Completed -> setDateState(it.outputData)
                    is OperationStatus.Plain.Error -> setErrorState(it.throwable)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun setDateState(isFirstLaunch: Boolean) {
        setViewState(MainViewState.Data(isFirstLaunch))
    }

    private fun setErrorState(throwable: Throwable) {
        setViewState(MainViewState.Error(throwable))
    }
}
