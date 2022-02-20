package ru.konohovalex.feature.notes.presentation.details.ui.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.konohovalex.core.design.model.Theme
import ru.konohovalex.core.ui.R
import ru.konohovalex.core.ui.compose.ThemedImage
import ru.konohovalex.core.ui.model.ImageWrapper

// TODO("https://compose.academy/blog/building_a_multi-action_floating_action_button_in_jetpack_compose")
@Composable
internal fun NoteDetailsFloatingActionButton() {
    FloatingActionButton(
        onClick = {
        },
        modifier = Modifier
            .padding(PaddingValues(Theme.sizes.border)),
        backgroundColor = Theme.palette.fillEnabledColor,
        shape = Theme.shapes.medium,
    ) {
        ThemedImage(
            imageWrapper = ImageWrapper.ImageResource(resourceId = R.drawable.ic_plus),
        )
    }
}
