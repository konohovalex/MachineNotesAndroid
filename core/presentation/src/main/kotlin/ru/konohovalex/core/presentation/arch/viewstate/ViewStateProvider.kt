package ru.konohovalex.core.presentation.arch.viewstate

import androidx.lifecycle.LiveData

interface ViewStateProvider<VS : ViewState> {
    val viewState: LiveData<VS>

    fun setViewState(viewState: VS)

    fun <VS> withViewState(viewStateClass: Class<VS>, block: (viewState: VS) -> Unit)
}
