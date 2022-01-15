package ru.konohovalex.feature.notes.presentation.list.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import ru.konohovalex.core.design.Theme
import ru.konohovalex.feature.notes.presentation.list.model.NotePreviewUiModel
import ru.konohovalex.feature.notes.presentation.utils.createNotePreviewDummyModelList

@Composable
internal fun NoteList(
    items: List<NotePreviewUiModel>,
    onNoteClick: (noteId: String) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(Theme.paddings.contentDefault),
        verticalArrangement = Arrangement.spacedBy(Theme.paddings.contentDefault)
    ) {
        items(items = items) { item ->
            NoteListItem(
                notePreviewUiModel = item,
                onClickListener = onNoteClick,
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
        NoteList(
            items = createNotePreviewDummyModelList(10),
            onNoteClick = {}
        )
    }
}
