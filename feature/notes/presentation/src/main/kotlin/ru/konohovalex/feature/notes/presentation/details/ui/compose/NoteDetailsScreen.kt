package ru.konohovalex.feature.notes.presentation.details.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
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
import ru.konohovalex.core.ui.compose.ThemedCard
import ru.konohovalex.core.ui.compose.ThemedCircularProgressBar
import ru.konohovalex.core.ui.extension.toTextWrapper
import ru.konohovalex.feature.notes.presentation.details.model.NoteDetailsViewEvent
import ru.konohovalex.feature.notes.presentation.details.model.NoteDetailsViewState
import ru.konohovalex.feature.notes.presentation.details.model.NoteUiModel

@Composable
internal fun NoteDetailsScreen(
    noteId: String?,
    viewEventHandler: ViewEventHandler<NoteDetailsViewEvent>,
    viewStateProvider: ViewStateProvider<NoteDetailsViewState>,
    navigateBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            NoteDetailsTopAppBar(navigateBack)
        },
        floatingActionButton = {
            NoteDetailsFloatingActionButton()
        },
        backgroundColor = Theme.palette.backgroundColor,
    ) {
        ThemedCard(
            modifier = Modifier
                .fillMaxSize()
                .padding(Theme.paddings.contentSmall),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                val viewState by viewStateProvider.viewState.observeAsState()

                processViewState(
                    noteId = noteId,
                    viewState = viewState,
                    viewEventHandler = viewEventHandler,
                )
            }
        }
    }
}

@Composable
private fun processViewState(
    noteId: String?,
    viewState: NoteDetailsViewState?,
    viewEventHandler: ViewEventHandler<NoteDetailsViewEvent>,
) = viewState?.let {
    when (it) {
        is NoteDetailsViewState.Idle -> LaunchedEffect(true) {
            viewEventHandler.handle(NoteDetailsViewEvent.GetNoteDetails(noteId))
        }
        is NoteDetailsViewState.Loading -> LoadingState()
        is NoteDetailsViewState.Data -> with(it) {
            DataState(noteUiModel) { noteContent ->
                viewEventHandler.handle(NoteDetailsViewEvent.NoteContentChanged(noteContent))
            }
        }
        is NoteDetailsViewState.Error -> with(it) {
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
    noteUiModel: NoteUiModel,
    onTextChanged: (String) -> Unit,
) {
    NoteEditionView(noteUiModel, onTextChanged)
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
