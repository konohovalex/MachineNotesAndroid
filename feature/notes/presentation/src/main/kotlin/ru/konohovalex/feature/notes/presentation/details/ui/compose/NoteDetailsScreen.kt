package ru.konohovalex.feature.notes.presentation.details.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import ru.konohovalex.core.design.model.Theme
import ru.konohovalex.core.presentation.arch.viewevent.ViewEventHandler
import ru.konohovalex.core.presentation.arch.viewstate.ViewStateProvider
import ru.konohovalex.core.ui.compose.ThemedCard
import ru.konohovalex.core.ui.compose.ThemedCircularProgressBar
import ru.konohovalex.core.ui.compose.ThemedText
import ru.konohovalex.core.ui.compose.ThemedTextType
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
            val viewState = viewStateProvider.viewState.observeAsState()

            when (val viewStateValue = viewState.value) {
                is NoteDetailsViewState.Idle -> viewEventHandler.handle(NoteDetailsViewEvent.GetNoteDetails(noteId))
                is NoteDetailsViewState.Loading -> LoadingState()
                is NoteDetailsViewState.Data -> DataState(viewStateValue.noteUiModel)
                is NoteDetailsViewState.Error -> ErrorState()
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
        // tbd this is just a dummy implementation, don't forget about remember calls
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
private fun ErrorState() {
    // tbd implement error state
}
