package ru.konohovalex.core.ui.compose.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.painterResource
import ru.konohovalex.core.ui.compose.model.ImageWrapper

@Composable
fun ImageWrapper.unwrap() = when (this) {
    is ImageWrapper.ImageResource -> painterResource(id = resourceId)
    is ImageWrapper.PlainImage -> BitmapPainter(value)
}
