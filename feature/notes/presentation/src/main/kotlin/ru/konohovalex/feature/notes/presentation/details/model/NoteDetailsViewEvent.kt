package ru.konohovalex.feature.notes.presentation.details.model

import ru.konohovalex.core.presentation.arch.viewevent.ViewEvent

internal sealed class NoteDetailsViewEvent : ViewEvent {
    data class GetNoteDetails(val noteId: String?) : NoteDetailsViewEvent()

    data class NoteContentChanged(val noteContent: String) : NoteDetailsViewEvent()
}
