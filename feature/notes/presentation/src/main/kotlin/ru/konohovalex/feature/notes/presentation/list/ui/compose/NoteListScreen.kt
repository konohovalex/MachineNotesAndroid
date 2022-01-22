package ru.konohovalex.feature.notes.presentation.list.ui.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import ru.konohovalex.core.design.Theme
import ru.konohovalex.core.presentation.arch.viewevent.ViewEventHandler
import ru.konohovalex.core.presentation.arch.viewstate.ViewStateProvider
import ru.konohovalex.core.ui.compose.ThemedCircularProgressBar
import ru.konohovalex.feature.notes.presentation.list.model.NoteListScreenViewEvent
import ru.konohovalex.feature.notes.presentation.list.model.NoteListViewState
import ru.konohovalex.feature.notes.presentation.list.model.NotePreviewUiModel

@Composable
internal fun NoteListScreen(
    viewEventHandler: ViewEventHandler<NoteListScreenViewEvent>,
    viewStateProvider: ViewStateProvider<NoteListViewState>,
    onNoteClick: (noteId: String?) -> Unit,
) {
    // tbd too much recompositions
    Scaffold(
        topBar = {
            NoteListTopAppBar(viewEventHandler)
        },
        floatingActionButton = {
            NoteListFloatingActionButton(onNoteClick)
        },
        backgroundColor = Theme.palette.backgroundColor,
    ) {
        val viewState = viewStateProvider.viewState.observeAsState()

        when (val viewStateValue = viewState.value) {
            is NoteListViewState.Idle -> viewEventHandler.handle(NoteListScreenViewEvent.GetNotes(filter = ""))
            is NoteListViewState.Loading -> LoadingState()
            is NoteListViewState.Data -> DataState(
                notes = viewStateValue.notes,
                onNoteClick = onNoteClick,
            )
            is NoteListViewState.Error -> ErrorState()
        }
    }
}

@Composable
private fun LoadingState() {
    ThemedCircularProgressBar(
        modifier = Modifier
            .fillMaxSize(),
    )
}

@Composable
private fun DataState(
    notes: List<NotePreviewUiModel>,
    onNoteClick: (noteId: String?) -> Unit,
) {
    // tbd implement no items state
    NoteList(
        items = notes,
        onNoteClick = onNoteClick,
    )
}

@Composable
private fun ErrorState() {
    // tbd implement error state (for both first and further pages)
}
