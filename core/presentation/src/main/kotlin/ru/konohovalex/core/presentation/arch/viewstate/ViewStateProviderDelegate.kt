package ru.konohovalex.core.presentation.arch.viewstate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.konohovalex.core.utils.extension.runIfInstance

class ViewStateProviderDelegate<VS : ViewState>(initialValue: VS) : ViewStateProvider<VS> {
    private val _viewState = MutableLiveData(initialValue)
    override val viewState: LiveData<VS> = _viewState

    override fun setViewState(viewState: VS) {
        _viewState.value = viewState
    }

    override fun <VS> withViewState(viewStateClass: Class<VS>, block: (viewState: VS) -> Unit) {
        runIfInstance(viewState.value, viewStateClass) { block.invoke(it) }
    }
}
