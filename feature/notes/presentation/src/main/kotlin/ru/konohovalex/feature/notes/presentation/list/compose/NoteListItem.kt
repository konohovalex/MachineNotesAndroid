package ru.konohovalex.feature.notes.presentation.list.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.konohovalex.core.design.Theme
import ru.konohovalex.core.ui.compose.ThemedCard
import ru.konohovalex.core.ui.compose.ThemedText
import ru.konohovalex.core.ui.compose.ThemedTextType
import ru.konohovalex.feature.notes.presentation.list.model.NotePreviewUiModel
import ru.konohovalex.feature.notes.presentation.list.model.TextWrapper

@Composable
internal fun NoteListItem(
    notePreviewUiModel: NotePreviewUiModel,
    onClickListener: (String) -> Unit,
) {
    with(notePreviewUiModel) {
        ThemedCard(
            modifier = Modifier
                .clickable {
                    onClickListener.invoke(notePreviewUiModel.id)
                }
                .fillMaxWidth()
        ) {
            ContentColumn() {
                TitleText(
                    text = unwrapText(titleTextWrapper),
                )

                SubtitleAndInfoRow(
                    subtitleText = unwrapText(subtitleTextWrapper),
                    infoText = dateTimeLastEdited.getDateTimeString(),
                )
            }
        }
    }
}

@Composable
private fun ContentColumn(
    content: @Composable ColumnScope.() -> Unit,
) = Column(
    modifier = Modifier
        .padding(
            vertical = 24.dp,
            horizontal = 32.dp,
        ),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    content = content,
)

@Composable
private fun TitleText(
    text: String,
) = ThemedText(
    themedTextType = ThemedTextType.TITLE,
    text = text,
    overflow = TextOverflow.Ellipsis,
    maxLines = 1,
)

@Composable
private fun SubtitleAndInfoRow(
    subtitleText: String,
    infoText: String,
) = Row(
    modifier = Modifier
        .fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(32.dp),
    verticalAlignment = Alignment.CenterVertically,
) {
    SubtitleText(
        text = subtitleText,
        modifier = Modifier
            .weight(1f)
    )
    InfoText(
        text = infoText,
        modifier = Modifier
            .wrapContentWidth()
    )
}

@Composable
private fun SubtitleText(
    text: String,
    modifier: Modifier,
) = ThemedText(
    themedTextType = ThemedTextType.SUBTITLE,
    text = text,
    modifier = modifier,
    maxLines = 1,
    overflow = TextOverflow.Ellipsis,
)

@Composable
private fun InfoText(
    text: String,
    modifier: Modifier,
) = ThemedText(
    themedTextType = ThemedTextType.INFO,
    text = text,
    modifier = modifier,
    textAlign = TextAlign.End,
    maxLines = 1,
)

@Composable
private fun unwrapText(textWrapper: TextWrapper) = when (textWrapper) {
    is TextWrapper.PlainText -> textWrapper.value
    is TextWrapper.StringResource -> stringResource(id = textWrapper.resourceId)
}

@Preview
@Composable
private fun NoteListItemPreview() {
    val notePreviewUiModel = createNotePreviewDummyModel("1", 0)
    Theme(darkTheme = true) {
        NoteListItem(notePreviewUiModel) {}
    }
}
