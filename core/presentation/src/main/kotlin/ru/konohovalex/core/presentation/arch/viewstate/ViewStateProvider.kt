package ru.konohovalex.core.presentation.arch.viewstate

import androidx.lifecycle.LiveData

interface ViewStateProvider<S : ViewState> {
    val viewState: LiveData<S>

    fun setViewState(viewState: S)

    fun withViewState(block: (viewState: S) -> Unit)
}
