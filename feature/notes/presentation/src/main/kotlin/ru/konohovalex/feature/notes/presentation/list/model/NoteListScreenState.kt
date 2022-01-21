package ru.konohovalex.feature.notes.presentation.list.model

import ru.konohovalex.core.presentation.arch.state.ScreenState

internal sealed class NoteListScreenState : ScreenState {
    object Idle : NoteListScreenState()

    object Loading : NoteListScreenState()

    data class Data(val notes: List<NotePreviewUiModel>) : NoteListScreenState()

    data class Error(val throwable: Throwable) : NoteListScreenState()
}
