package ru.konohovalex.feature.notes.presentation.details.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import ru.konohovalex.core.ui.compose.ThemedCard
import ru.konohovalex.core.ui.compose.ThemedCircularProgressBar
import ru.konohovalex.core.ui.compose.ThemedText
import ru.konohovalex.core.ui.compose.ThemedTextType
import ru.konohovalex.core.ui.extension.toTextWrapper
import ru.konohovalex.core.ui.extension.unwrap
import ru.konohovalex.core.ui.model.TextWrapper
import ru.konohovalex.feature.notes.presentation.details.model.NoteContentUiModel
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
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
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
            val viewState by viewStateProvider.viewState.observeAsState()

            when (viewState) {
                is NoteDetailsViewState.Idle -> LaunchedEffect(true) {
                    viewEventHandler.handle(NoteDetailsViewEvent.GetNoteDetails(noteId))
                }
                is NoteDetailsViewState.Loading -> LoadingState()
                is NoteDetailsViewState.Data -> with(viewState as NoteDetailsViewState.Data) {
                    DataState(noteUiModel)
                }
                is NoteDetailsViewState.Error -> with(viewState as NoteDetailsViewState.Error) {
                    ErrorState(throwable, onActionButtonClick)
                }
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
private fun DataState(noteUiModel: NoteUiModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Theme.paddings.contentSmall),
        verticalArrangement = Arrangement.spacedBy(Theme.paddings.contentExtraSmall)
    ) {
        // tbd don't forget about rememberSaveable
        with(noteUiModel) {
            title.unwrap()
                .takeIf { it.isNotBlank() }
                ?.let {
                    ThemedText(
                        themedTextType = ThemedTextType.TITLE,
                        textWrapper = title,
                    )
                }

            val noteContent = noteContent.getOrNull(0)
            ThemedText(
                themedTextType = ThemedTextType.BODY,
                textWrapper = TextWrapper.PlainText(
                    value = when (noteContent) {
                        is NoteContentUiModel.Text -> noteContent.content
                        is NoteContentUiModel.Image -> noteContent.contentUrl
                        is NoteContentUiModel.Audio -> noteContent.contentUrl
                        else -> "null"
                    }
                ),
            )
        }
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
