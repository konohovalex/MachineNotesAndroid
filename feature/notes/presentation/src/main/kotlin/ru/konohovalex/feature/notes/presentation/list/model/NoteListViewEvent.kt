package ru.konohovalex.feature.notes.presentation.list.model

import ru.konohovalex.core.presentation.arch.viewevent.ViewEvent

sealed class NoteListViewEvent : ViewEvent {
    data class GetNotes(val filter: String) : NoteListViewEvent()

    data class DeleteNote(val noteId: String) : NoteListViewEvent()

    object ErrorProcessed : NoteListViewEvent()
}
