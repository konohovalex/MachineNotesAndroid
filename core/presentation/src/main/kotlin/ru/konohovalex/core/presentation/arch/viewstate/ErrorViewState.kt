package ru.konohovalex.core.presentation.arch.viewstate

interface ErrorViewState : ViewState {
    val throwable: Throwable
    val onActionButtonClick: () -> Unit
}
