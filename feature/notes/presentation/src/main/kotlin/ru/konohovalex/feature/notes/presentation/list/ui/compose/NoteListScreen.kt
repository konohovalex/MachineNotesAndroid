package ru.konohovalex.feature.notes.presentation.list.ui.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import ru.konohovalex.core.design.Theme
import ru.konohovalex.core.presentation.arch.event.EventHandler
import ru.konohovalex.core.presentation.arch.state.ScreenStateProvider
import ru.konohovalex.core.ui.compose.ThemedCircularProgressBar
import ru.konohovalex.feature.notes.presentation.list.NoteListViewModel
import ru.konohovalex.feature.notes.presentation.list.model.NoteListScreenEvent
import ru.konohovalex.feature.notes.presentation.list.model.NoteListScreenState
import ru.konohovalex.feature.notes.presentation.list.model.NotePreviewUiModel

@Composable
internal fun NoteListScreen(
    eventHandler: EventHandler<NoteListScreenEvent> = hiltViewModel<NoteListViewModel>(),
    screenStateProvider: ScreenStateProvider<NoteListScreenState> = hiltViewModel<NoteListViewModel>(),
    onNoteClick: (noteId: String?) -> Unit,
) {
    Scaffold(
        topBar = {
            NoteListTopAppBar(eventHandler)
        },
        floatingActionButton = {
            NoteListFloatingActionButton(onNoteClick)
        },
        backgroundColor = Theme.palette.backgroundColor,
    ) {
        // tbd too much recompositions called as PaddingValues changing frequently
        val screenState = screenStateProvider.screenState.observeAsState()

        when (val screenStateValue = screenState.value) {
            is NoteListScreenState.Idle -> eventHandler.handle(NoteListScreenEvent.GetNotes(filter = ""))
            is NoteListScreenState.Loading -> LoadingState()
            is NoteListScreenState.Data -> DataState(
                notes = screenStateValue.notes,
                onNoteClick = onNoteClick,
            )
            is NoteListScreenState.Error -> ErrorState()
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
