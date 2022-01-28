package ru.konohovalex.feature.notes.presentation.list.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import ru.konohovalex.core.design.model.Theme
import ru.konohovalex.feature.notes.presentation.list.model.NotePreviewUiModel
import ru.konohovalex.feature.notes.presentation.extension.createNotePreviewDummyModelList

@Composable
internal fun NoteList(
    items: List<NotePreviewUiModel>,
    onNoteClick: (noteId: String) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(Theme.paddings.contentSmall),
        verticalArrangement = Arrangement.spacedBy(Theme.paddings.contentSmall)
    ) {
        items(items = items) { item ->
            NoteListItem(
                notePreviewUiModel = item,
                onClick = onNoteClick,
            )
        }
    }
}

@Preview(
    device = Devices.PIXEL_4,
)
@Composable
private fun NotePreviewListPreview() {
    Theme(darkTheme = false) {
        NoteList(items = createNotePreviewDummyModelList(10)) {}
    }
}
