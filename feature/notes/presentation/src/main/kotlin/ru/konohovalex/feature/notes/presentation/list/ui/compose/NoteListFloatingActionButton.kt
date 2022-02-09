package ru.konohovalex.feature.notes.presentation.list.ui.compose

import androidx.compose.material.FloatingActionButton
import androidx.compose.runtime.Composable
import ru.konohovalex.core.design.model.Theme
import ru.konohovalex.core.ui.R
import ru.konohovalex.core.ui.compose.ThemedImage
import ru.konohovalex.core.ui.model.ImageWrapper

@Composable
internal fun NoteListFloatingActionButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        backgroundColor = Theme.palette.fillEnabledColor,
        shape = Theme.shapes.medium,
    ) {
        ThemedImage(
            imageWrapper = ImageWrapper.ImageResource(resourceId = R.drawable.ic_plus),
        )
    }
}
