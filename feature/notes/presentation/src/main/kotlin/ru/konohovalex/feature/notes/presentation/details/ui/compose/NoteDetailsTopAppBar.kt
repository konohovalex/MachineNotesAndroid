package ru.konohovalex.feature.notes.presentation.details.ui.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.konohovalex.core.design.Theme
import ru.konohovalex.core.ui.compose.ThemedImage
import ru.konohovalex.core.ui.compose.ThemedText
import ru.konohovalex.core.ui.compose.ThemedTextType
import ru.konohovalex.core.ui.compose.model.ImageWrapper
import ru.konohovalex.core.ui.compose.model.TextWrapper
import ru.konohovalex.feature.notes.presentation.R

@Composable
internal fun NoteDetailsTopAppBar() {
    TopAppBar(
        title = {
            ThemedText(
                themedTextType = ThemedTextType.HEADLINE,
                textWrapper = TextWrapper.StringResource(resourceId = R.string.back),
            )
        },
        modifier = Modifier
            .fillMaxWidth(),
        backgroundColor = Theme.palette.backgroundColor,
        navigationIcon = {
            ThemedImage(
                imageWrapper = ImageWrapper.ImageResource(resourceId = R.drawable.ic_arrow_back),
            )
        },
    )
}

@Preview
@Composable
private fun NoteListTopAppBarPreview() {
    Theme {
        NoteDetailsTopAppBar()
    }
}
