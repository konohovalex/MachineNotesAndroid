package ru.konohovalex.feature.notes.presentation.details.model

import ru.konohovalex.core.presentation.arch.viewstate.ViewState

internal sealed class NoteDetailsViewState : ViewState {
    object Idle : NoteDetailsViewState()

    object Loading : NoteDetailsViewState()

    data class Data(val noteUiModel: NoteUiModel) : NoteDetailsViewState()

    data class Error(val throwable: Throwable) : NoteDetailsViewState()
}
