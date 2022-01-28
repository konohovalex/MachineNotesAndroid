package ru.konohovalex.core.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import ru.konohovalex.core.design.model.Theme
import ru.konohovalex.core.ui.model.ImageWrapper
import ru.konohovalex.core.ui.extension.unwrap

@Composable
fun ThemedImage(
    imageWrapper: ImageWrapper,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = imageWrapper.unwrap(),
        contentDescription = imageWrapper.contentDescription?.unwrap(),
        modifier = modifier,
        colorFilter = ColorFilter.tint(Theme.palette.iconTintColor),
    )
}
