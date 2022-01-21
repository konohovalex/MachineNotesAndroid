package ru.konohovalex.feature.notes.presentation.list.ui.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.konohovalex.core.design.Theme
import ru.konohovalex.core.ui.R
import ru.konohovalex.core.ui.compose.ThemedImage
import ru.konohovalex.core.ui.compose.model.ImageWrapper

@Composable
internal fun NoteListFloatingActionButton(onNoteClick: (noteId: String?) -> Unit) {
    FloatingActionButton(
        onClick = {
            onNoteClick.invoke(null)
        },
        modifier = Modifier
            .padding(PaddingValues(Theme.paddings.floatingActionButtonCompensation)),
        backgroundColor = Theme.palette.fillEnabledColor,
        shape = Theme.shapes.medium,
    ) {
        ThemedImage(
            imageWrapper = ImageWrapper.ImageResource(resourceId = R.drawable.ic_plus),
        )
    }
}
