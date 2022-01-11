package ru.konohovalex.feature.notes.presentation.list.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.konohovalex.core.design.Theme
import ru.konohovalex.feature.notes.presentation.list.model.NotePreviewUiModel

@Composable
internal fun NoteList(
    items: List<NotePreviewUiModel>,
    onItemClickListener: (String) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = items) { item ->
            NoteListItem(
                notePreviewUiModel = item,
                onClickListener = onItemClickListener,
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
            items = createNotePreviewDummyModelList(25),
            onItemClickListener = {}
        )
    }
}
