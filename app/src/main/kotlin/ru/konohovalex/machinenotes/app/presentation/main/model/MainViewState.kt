package ru.konohovalex.machinenotes.app.presentation.main.model

import ru.konohovalex.core.presentation.arch.viewstate.ViewState

sealed class MainViewState : ViewState {
    object Idle : MainViewState()

    data class Data(val isFirstLaunch: Boolean) : MainViewState()

    data class Error(val throwable: Throwable) : MainViewState()
}
