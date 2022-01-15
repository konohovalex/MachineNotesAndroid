package ru.konohovalex.core.ui.compose.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.ImageBitmap

sealed class ImageWrapper(open val contentDescription: TextWrapper?) {
    data class ImageResource(
        override val contentDescription: TextWrapper? = null,
        @DrawableRes val resourceId: Int,
    ) : ImageWrapper(contentDescription)

    data class PlainImage(
        override val contentDescription: TextWrapper? = null,
        val value: ImageBitmap,
    ) : ImageWrapper(contentDescription)
}
