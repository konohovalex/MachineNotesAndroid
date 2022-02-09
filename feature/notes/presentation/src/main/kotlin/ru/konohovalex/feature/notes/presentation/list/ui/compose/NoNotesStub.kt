package ru.konohovalex.feature.notes.presentation.list.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.konohovalex.core.design.model.Theme
import ru.konohovalex.core.ui.compose.ThemedImage
import ru.konohovalex.core.ui.compose.ThemedText
import ru.konohovalex.core.ui.compose.ThemedTextType
import ru.konohovalex.core.ui.model.ImageWrapper
import ru.konohovalex.core.ui.model.TextWrapper
import ru.konohovalex.feature.notes.presentation.R

@Composable
internal fun NoNotesStub() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .padding(Theme.paddings.contentSmall),
            verticalArrangement = Arrangement.spacedBy(Theme.paddings.contentMedium),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ThemedImage(
                imageWrapper = ImageWrapper.ImageResource(resourceId = R.drawable.ic_error),
                iconTintColor = Theme.palette.accentColor,
            )

            ThemedText(
                themedTextType = ThemedTextType.TITLE,
                textWrapper = TextWrapper.StringResource(resourceId = R.string.no_notes_title),
            )

            ThemedText(
                themedTextType = ThemedTextType.BODY,
                textWrapper = TextWrapper.StringResource(resourceId = R.string.no_notes_body),
            )
        }
    }
}
