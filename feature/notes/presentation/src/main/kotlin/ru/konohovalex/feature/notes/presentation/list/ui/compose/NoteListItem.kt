package ru.konohovalex.feature.notes.presentation.list.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberSwipeableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import ru.konohovalex.core.design.model.Theme
import ru.konohovalex.core.ui.compose.ThemedButton
import ru.konohovalex.core.ui.compose.ThemedCard
import ru.konohovalex.core.ui.compose.ThemedText
import ru.konohovalex.core.ui.compose.ThemedTextType
import ru.konohovalex.core.ui.extension.simpleSwipeable
import ru.konohovalex.core.ui.model.ButtonData
import ru.konohovalex.core.ui.model.SwipeDirection
import ru.konohovalex.core.ui.model.TextWrapper
import ru.konohovalex.feature.notes.presentation.R
import ru.konohovalex.feature.notes.presentation.extension.createNotePreviewDummyModel
import ru.konohovalex.feature.notes.presentation.list.model.NotePreviewUiModel

@ExperimentalMaterialApi
@Composable
internal fun NoteListItem(
    notePreviewUiModel: NotePreviewUiModel,
    onClick: (noteId: String) -> Unit,
    onDelete: (noteId: String) -> Unit,
) {
    Box(
        modifier = Modifier
            .height(IntrinsicSize.Max),
    ) {
        // TODO("can there be more optimal implementation?")
        val deleteButtonSizeState = remember {
            mutableStateOf(Size.Zero)
        }

        DeleteNoteCard(notePreviewUiModel, onDelete) {
            deleteButtonSizeState.value = Size(
                width = it.width.toFloat(),
                height = it.height.toFloat(),
            )
        }

        NotePreviewCard(notePreviewUiModel, onClick, deleteButtonSizeState)
    }
}

@Composable
private fun DeleteNoteCard(
    notePreviewUiModel: NotePreviewUiModel,
    onDelete: (noteId: String) -> Unit,
    onDeleteButtonSizeChanged: (IntSize) -> Unit,
) {
    ThemedCard(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        borderColor = Theme.palette.errorColor,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd,
        ) {
            DeleteNoteButton(
                notePreviewUiModel = notePreviewUiModel,
                onDelete = onDelete,
                onSizeChanged = onDeleteButtonSizeChanged,
            )
        }
    }
}

@Composable
private fun DeleteNoteButton(
    notePreviewUiModel: NotePreviewUiModel,
    onDelete: (noteId: String) -> Unit,
    onSizeChanged: (IntSize) -> Unit,
) {
    ThemedButton(
        buttonData = ButtonData.Text(
            content = ButtonData.Content.Text(
                textWrapper = TextWrapper.StringResource(resourceId = R.string.delete),
                textColor = Theme.palette.errorColor,
            ),
            onClick = {
                onDelete.invoke(notePreviewUiModel.id)
            },
        ),
        modifier = Modifier
            .padding(horizontal = Theme.paddings.contentMedium)
            .onSizeChanged {
                onSizeChanged.invoke(it)
            },
    )
}

@Composable
private fun NotePreviewCard(
    notePreviewUiModel: NotePreviewUiModel,
    onClick: (noteId: String) -> Unit,
    deleteButtonSizeState: State<Size>,
) {
    val swipeableState = rememberSwipeableState(SwipeDirection.INITIAL)

    with(LocalDensity.current) {
        val xOffset = swipeableState.offset.value.toDp()

        val deleteButtonWidth = deleteButtonSizeState.value.width
        val deleteButtonHorizontalPaddings = Theme.paddings.contentMedium.toPx() * 2
        val leftAnchor = remember(deleteButtonWidth) {
            if (deleteButtonWidth == 0f) 1f
            else deleteButtonWidth + deleteButtonHorizontalPaddings
        }
        ThemedCard(
            modifier = Modifier
                .fillMaxWidth()
                .offset(x = xOffset)
                .simpleSwipeable(
                    state = swipeableState,
                    anchors = mapOf(
                        -leftAnchor to SwipeDirection.LEFT,
                        0f to SwipeDirection.INITIAL,
                    ),
                ),
        ) {
            NotePreviewContent(notePreviewUiModel, onClick)
        }
    }
}

@Composable
private fun NotePreviewContent(
    notePreviewUiModel: NotePreviewUiModel,
    onClick: (noteId: String) -> Unit,
) {
    with(notePreviewUiModel) {
        Box(
            modifier = Modifier
                .clickable {
                    onClick.invoke(notePreviewUiModel.id)
                },
        ) {
            Column(
                modifier = Modifier
                    .padding(Theme.paddings.contentSmall),
                verticalArrangement = Arrangement.spacedBy(Theme.paddings.contentExtraSmall),
            ) {
                TitleText(
                    textWrapper = titleTextWrapper,
                )

                SubtitleAndInfoRow(
                    subtitleTextWrapper = subtitleTextWrapper,
                    infoTextWrapper = TextWrapper.PlainText(value = dateTimeLastEdited.getDateTimeString()),
                )
            }
        }
    }
}

@Composable
private fun TitleText(
    textWrapper: TextWrapper,
) = ThemedText(
    themedTextType = ThemedTextType.TITLE,
    textWrapper = textWrapper,
    overflow = TextOverflow.Ellipsis,
    maxLines = 1,
)

@Composable
private fun SubtitleAndInfoRow(
    subtitleTextWrapper: TextWrapper,
    infoTextWrapper: TextWrapper,
) = Row(
    modifier = Modifier
        .fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(Theme.paddings.contentSmall),
    verticalAlignment = Alignment.CenterVertically,
) {
    SubtitleText(
        textWrapper = subtitleTextWrapper,
        modifier = Modifier
            .weight(1f)
    )
    InfoText(
        textWrapper = infoTextWrapper,
        modifier = Modifier
            .wrapContentWidth()
    )
}

@Composable
private fun SubtitleText(
    textWrapper: TextWrapper,
    modifier: Modifier,
) = ThemedText(
    themedTextType = ThemedTextType.BODY,
    textWrapper = textWrapper,
    modifier = modifier,
    maxLines = 1,
    overflow = TextOverflow.Ellipsis,
)

@Composable
private fun InfoText(
    textWrapper: TextWrapper,
    modifier: Modifier,
) = ThemedText(
    themedTextType = ThemedTextType.LABEL,
    textWrapper = textWrapper,
    modifier = modifier,
    textAlign = TextAlign.End,
    maxLines = 1,
)

@Preview
@Composable
private fun NoteListItemPreview() {
    val notePreviewUiModel = createNotePreviewDummyModel("1", 0)
    Theme(darkTheme = true) {
        NoteListItem(notePreviewUiModel, {}) {}
    }
}
