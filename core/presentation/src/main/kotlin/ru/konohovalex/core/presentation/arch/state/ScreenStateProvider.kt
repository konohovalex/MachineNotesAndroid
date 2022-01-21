package ru.konohovalex.core.presentation.arch.state

import androidx.lifecycle.LiveData

interface ScreenStateProvider<S : ScreenState> {
    val screenState: LiveData<S>

    fun setScreenState(screenState: S)

    fun withScreenState(block: (screenState: S) -> Unit)
}
