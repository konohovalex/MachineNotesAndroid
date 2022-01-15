package ru.konohovalex.feature.notes.presentation.list.ui.compose

import androidx.compose.material.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.konohovalex.core.design.Theme
import ru.konohovalex.core.ui.R
import ru.konohovalex.core.ui.compose.ThemedImage
import ru.konohovalex.core.ui.compose.model.ImageWrapper

@Composable
internal fun NoteListFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        backgroundColor = Theme.palette.fillEnabledColor,
        shape = Theme.shapes.medium,
    ) {
        ThemedImage(
            imageWrapper = ImageWrapper.ImageResource(resourceId = R.drawable.ic_plus),
        )
    }
}
