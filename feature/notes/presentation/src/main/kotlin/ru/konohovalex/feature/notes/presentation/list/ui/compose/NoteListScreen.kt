package ru.konohovalex.feature.notes.presentation.list.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.konohovalex.core.design.model.Theme
import ru.konohovalex.core.presentation.arch.viewevent.ViewEventHandler
import ru.konohovalex.core.presentation.arch.viewstate.ViewStateProvider
import ru.konohovalex.core.ui.compose.ErrorCard
import ru.konohovalex.core.ui.compose.ThemedCircularProgressBar
import ru.konohovalex.core.ui.extension.toTextWrapper
import ru.konohovalex.feature.notes.presentation.list.model.NoteListViewEvent
import ru.konohovalex.feature.notes.presentation.list.model.NoteListViewState
import ru.konohovalex.feature.notes.presentation.list.model.NotePreviewUiModel

@Composable
internal fun NoteListScreen(
    viewEventHandler: ViewEventHandler<NoteListViewEvent>,
    viewStateProvider: ViewStateProvider<NoteListViewState>,
    navigateToNoteDetails: (noteId: String?) -> Unit,
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
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
        val viewState by viewStateProvider.viewState.observeAsState()

        when (viewState) {
            is NoteListViewState.Idle -> LaunchedEffect(true) {
                viewEventHandler.handle(NoteListViewEvent.GetNotes(filter = ""))
            }
            is NoteListViewState.Loading -> LoadingState()
            is NoteListViewState.Data -> with(viewState as NoteListViewState.Data) {
                DataState(
                    notes = notes,
                    onNoteClick = navigateToNoteDetails,
                    throwable = throwable,
                )
            }
            is NoteListViewState.Error -> with(viewState as NoteListViewState.Error) {
                ErrorState(throwable, onActionButtonClick)
            }
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
    throwable: Throwable?,
) {
    // tbd implement no items state
    // tbd error state for not first page
    NoteList(
        items = notes,
        onNoteClick = onNoteClick,
    )
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
