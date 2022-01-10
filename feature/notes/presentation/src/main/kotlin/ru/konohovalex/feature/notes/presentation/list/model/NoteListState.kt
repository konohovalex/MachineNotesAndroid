package ru.konohovalex.feature.notes.presentation.list.model

sealed class NoteListState {
    object Loading : NoteListState()

    data class Data(val notes: List<NotePreviewUiModel>) : NoteListState()

    data class Error(val throwable: Throwable) : NoteListState()
}
