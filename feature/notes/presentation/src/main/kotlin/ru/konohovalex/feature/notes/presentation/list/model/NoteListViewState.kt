package ru.konohovalex.feature.notes.presentation.list.model

import ru.konohovalex.core.presentation.arch.viewstate.ErrorViewState
import ru.konohovalex.core.presentation.arch.viewstate.ViewState

internal sealed class NoteListViewState : ViewState {
    object Idle : NoteListViewState()

    object Loading : NoteListViewState()

    data class Data(
        val notes: List<NotePreviewUiModel>,
        val throwable: Throwable? = null,
    ) : NoteListViewState()

    data class Error(
        override val throwable: Throwable,
        override val onActionButtonClick: () -> Unit,
    ) : NoteListViewState(), ErrorViewState
}
