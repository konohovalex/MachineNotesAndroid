package ru.konohovalex.feature.notes.presentation.list.model

import ru.konohovalex.core.presentation.arch.viewevent.ViewEvent

sealed class NoteListViewEvent : ViewEvent {
    data class GetNotes(val filter: String) : NoteListViewEvent()
}
