package ru.konohovalex.core.presentation.arch.state

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.konohovalex.core.utils.safeCast

class ScreenStateProviderDelegate<S : ScreenState>(initialValue: S) : ScreenStateProvider<S> {
    private val _screenState = MutableLiveData(initialValue)
    override val screenState: LiveData<S> = _screenState

    override fun setScreenState(screenState: S) {
        _screenState.value = screenState
    }

    override fun withScreenState(block: (screenState: S) -> Unit) {
        screenState.value?.let(block::invoke)
    }
}
