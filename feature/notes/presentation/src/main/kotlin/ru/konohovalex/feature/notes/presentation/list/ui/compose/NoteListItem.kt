package ru.konohovalex.feature.notes.presentation.list.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import ru.konohovalex.core.design.model.Theme
import ru.konohovalex.core.ui.compose.ThemedCard
import ru.konohovalex.core.ui.compose.ThemedText
import ru.konohovalex.core.ui.compose.ThemedTextType
import ru.konohovalex.core.ui.model.TextWrapper
import ru.konohovalex.feature.notes.presentation.list.model.NotePreviewUiModel
import ru.konohovalex.feature.notes.presentation.extension.createNotePreviewDummyModel

@Composable
internal fun NoteListItem(
    notePreviewUiModel: NotePreviewUiModel,
    onClick: (noteId: String) -> Unit,
) {
    with(notePreviewUiModel) {
        ThemedCard(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Box(
                modifier = Modifier
                    .clickable {
                        onClick.invoke(notePreviewUiModel.id)
                    },
            ) {
                ContentColumn() {
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
}

@Composable
private fun ContentColumn(
    content: @Composable ColumnScope.() -> Unit,
) = Column(
    modifier = Modifier
        .padding(Theme.paddings.contentSmall),
    verticalArrangement = Arrangement.spacedBy(Theme.paddings.contentExtraSmall),
    content = content,
)

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
        NoteListItem(notePreviewUiModel) {}
    }
}
