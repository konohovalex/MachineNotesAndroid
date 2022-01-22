package ru.konohovalex.feature.notes.presentation.list.model

import ru.konohovalex.core.presentation.arch.viewstate.ViewState

internal sealed class NoteListViewState : ViewState {
    object Idle : NoteListViewState()

    object Loading : NoteListViewState()

    data class Data(val notes: List<NotePreviewUiModel>) : NoteListViewState()

    data class Error(val throwable: Throwable) : NoteListViewState()
}
