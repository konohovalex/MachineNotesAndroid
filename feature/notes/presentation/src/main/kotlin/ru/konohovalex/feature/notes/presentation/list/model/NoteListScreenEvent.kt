package ru.konohovalex.feature.notes.presentation.list.model

import ru.konohovalex.core.presentation.arch.event.Event

sealed class NoteListScreenEvent: Event {
    data class GetNotes(val filter: String) : NoteListScreenEvent()
}
