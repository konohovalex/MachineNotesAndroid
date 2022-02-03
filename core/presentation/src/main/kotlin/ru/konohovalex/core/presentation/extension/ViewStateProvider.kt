package ru.konohovalex.core.presentation.extension

import ru.konohovalex.core.presentation.arch.viewstate.ErrorViewState
import ru.konohovalex.core.presentation.arch.viewstate.ViewState
import ru.konohovalex.core.presentation.arch.viewstate.ViewStateProvider
import ru.konohovalex.core.utils.extension.safeCast

inline fun <reified VS : ViewState, ES : ErrorViewState> ViewStateProvider<VS>.setErrorViewState(errorState: ES) {
    errorState.safeCast<VS>()?.let { setViewState(it) }
}
