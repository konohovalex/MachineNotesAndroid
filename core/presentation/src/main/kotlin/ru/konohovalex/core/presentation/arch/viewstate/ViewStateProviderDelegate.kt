package ru.konohovalex.core.presentation.arch.viewstate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ViewStateProviderDelegate<S : ViewState>(initialValue: S) : ViewStateProvider<S> {
    private val _viewState = MutableLiveData(initialValue)
    override val viewState: LiveData<S> = _viewState

    override fun setViewState(viewState: S) {
        _viewState.value = viewState
    }

    override fun withViewState(block: (viewState: S) -> Unit) {
        viewState.value?.let(block::invoke)
    }
}
