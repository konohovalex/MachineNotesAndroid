package ru.konohovalex.feature.notes.presentation.list.model

import ru.konohovalex.core.presentation.arch.viewevent.ViewEvent

sealed class NoteListScreenViewEvent: ViewEvent {
    data class GetNotes(val filter: String) : NoteListScreenViewEvent()
}
