package ru.konohovalex.feature.notes.presentation.list.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.konohovalex.core.design.model.Theme
import ru.konohovalex.feature.notes.presentation.extension.createNotePreviewDummyModelList
import ru.konohovalex.feature.notes.presentation.list.model.NotePreviewUiModel

@Composable
internal fun NoteList(
    items: List<NotePreviewUiModel>,
    onNoteClick: (noteId: String) -> Unit,
    onDeleteNote: (noteId: String) -> Unit,
) {
    // tbd error state for not first page

    /** See [androidx.compose.material.FloatingActionButton.FabSize] */
    val fabSize = 56.dp

    /** See [androidx.compose.material.Scaffold.FabSpacing] */
    val fabSpacing = 16.dp

    /** We must add bottom padding, so FAB will not overlap lowest item's content */
    val bottomPadding = fabSize + fabSpacing + Theme.paddings.contentMedium

    LazyColumn(
        contentPadding = PaddingValues(
            start = Theme.paddings.contentSmall,
            top = Theme.paddings.contentSmall,
            end = Theme.paddings.contentSmall,
            bottom = bottomPadding,
        ),
        verticalArrangement = Arrangement.spacedBy(Theme.paddings.contentSmall)
    ) {
        items(
            items = items,
            key = {
                it.id
            },
        ) { item ->
            NoteListItem(
                notePreviewUiModel = item,
                onClick = onNoteClick,
                onDelete = onDeleteNote,
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
        NoteList(items = createNotePreviewDummyModelList(10), {}) {}
    }
}
