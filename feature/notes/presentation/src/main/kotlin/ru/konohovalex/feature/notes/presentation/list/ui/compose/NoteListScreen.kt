package ru.konohovalex.feature.notes.presentation.list.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import ru.konohovalex.core.design.model.Theme
import ru.konohovalex.core.presentation.arch.viewevent.ViewEventHandler
import ru.konohovalex.core.presentation.arch.viewstate.ViewStateProvider
import ru.konohovalex.core.ui.compose.ErrorCard
import ru.konohovalex.core.ui.compose.ThemedCircularProgressBar
import ru.konohovalex.core.ui.compose.ThemedSnackbarHost
import ru.konohovalex.core.ui.extension.toTextWrapper
import ru.konohovalex.core.utils.extension.safeCast
import ru.konohovalex.feature.notes.presentation.list.model.NoteListViewEvent
import ru.konohovalex.feature.notes.presentation.list.model.NoteListViewState
import ru.konohovalex.feature.notes.presentation.list.model.NotePreviewUiModel

@Composable
internal fun NoteListScreen(
    viewEventHandler: ViewEventHandler<NoteListViewEvent>,
    viewStateProvider: ViewStateProvider<NoteListViewState>,
    navigateToNoteDetails: (noteId: String?) -> Unit,
) {
    val viewState by viewStateProvider.viewState.observeAsState()

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { snackbarHostState ->
            viewState.safeCast<NoteListViewState.Data>()?.let {
                SnackbarHost(
                    viewState = it,
                    viewEventHandler = viewEventHandler,
                    snackbarHostState = snackbarHostState,
                    coroutineScope = coroutineScope,
                )
            }
        },
        topBar = {
            NoteListTopAppBar { searchInput: String ->
                viewEventHandler.handle(NoteListViewEvent.GetNotes(filter = searchInput))
            }
        },
        floatingActionButton = {
            NoteListFloatingActionButton {
                navigateToNoteDetails.invoke(null)
            }
        },
        backgroundColor = Theme.palette.backgroundColor,
    ) {
        processViewState(
            viewState,
            viewEventHandler,
            navigateToNoteDetails,
        )
    }
}

@Composable
private fun SnackbarHost(
    viewState: NoteListViewState.Data,
    viewEventHandler: ViewEventHandler<NoteListViewEvent>,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
) {
    viewState.throwable?.let {
        ThemedSnackbarHost(
            messageTextWrapper = it.toTextWrapper(),
            snackbarHostState = snackbarHostState,
            coroutineScope = coroutineScope,
            onDismissed = {
                viewEventHandler.handle(NoteListViewEvent.ErrorProcessed)
            },
        )
    }
}

@Composable
private fun processViewState(
    viewState: NoteListViewState?,
    viewEventHandler: ViewEventHandler<NoteListViewEvent>,
    navigateToNoteDetails: (noteId: String?) -> Unit,
) = viewState?.let {
    when (it) {
        is NoteListViewState.Idle -> LaunchedEffect(true) {
            viewEventHandler.handle(NoteListViewEvent.GetNotes(filter = ""))
        }
        is NoteListViewState.Loading -> LoadingState()
        is NoteListViewState.Data -> with(it) {
            DataState(
                notes = notes,
                onNoteClick = navigateToNoteDetails,
                onDeleteNote = { noteId ->
                    viewEventHandler.handle(NoteListViewEvent.DeleteNote(noteId))
                },
            )
        }
        is NoteListViewState.Error -> with(it) {
            ErrorState(throwable, onActionButtonClick)
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
    onDeleteNote: (noteId: String) -> Unit,
) {
    if (notes.isNotEmpty()) {
        NoteList(
            items = notes,
            onNoteClick = onNoteClick,
            onDeleteNote = onDeleteNote,
        )
    }
    else {
        NoNotesStub()
    }
}

@Composable
private fun ErrorState(
    throwable: Throwable,
    onActionButtonClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        ErrorCard(
            descriptionTextWrapper = throwable.toTextWrapper(),
            onActionButtonClick = onActionButtonClick,
        )
    }
}
