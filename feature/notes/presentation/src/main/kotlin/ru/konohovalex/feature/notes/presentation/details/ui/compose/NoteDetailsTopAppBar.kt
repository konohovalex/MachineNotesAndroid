package ru.konohovalex.feature.notes.presentation.details.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.konohovalex.core.design.model.Theme
import ru.konohovalex.core.ui.compose.ThemedImage
import ru.konohovalex.core.ui.compose.ThemedText
import ru.konohovalex.core.ui.compose.ThemedTextType
import ru.konohovalex.core.ui.model.ImageWrapper
import ru.konohovalex.core.ui.model.TextWrapper
import ru.konohovalex.feature.notes.presentation.R

@Composable
internal fun NoteDetailsTopAppBar(onBackButtonClick: () -> Unit) {
    Surface(
        color = Theme.palette.backgroundColor,
        elevation = Theme.materialElevations.topAppBar,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    PaddingValues(
                        horizontal = Theme.paddings.contentMedium,
                        vertical = Theme.paddings.contentSmall,
                    )
                ),
            horizontalArrangement = Arrangement.spacedBy(Theme.paddings.contentMedium),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onBackButtonClick) {
                ThemedImage(
                    imageWrapper = ImageWrapper.ImageResource(resourceId = R.drawable.ic_arrow_back),
                )
            }

            ThemedText(
                themedTextType = ThemedTextType.HEADLINE,
                textWrapper = TextWrapper.StringResource(resourceId = R.string.back),
            )
        }
    }
}

@Preview
@Composable
private fun NoteListTopAppBarPreview() {
    Theme {
        NoteDetailsTopAppBar {}
    }
}
