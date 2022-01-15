package ru.konohovalex.core.ui.compose.model

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

sealed class ButtonData(
    open val enabled: Boolean = true,
    open val onClickListener: () -> Unit,
) {
    data class Regular(
        override val enabled: Boolean = true,
        override val onClickListener: () -> Unit,
        val content: List<Content>,
        val contentArrangement: ContentArrangement = ContentArrangement.HORIZONTAL,
        val contentSpacing: Dp = 0.dp,
    ) : ButtonData(
        enabled = enabled,
        onClickListener = onClickListener,
    )

    open class Outlined(
        override val enabled: Boolean = true,
        override val onClickListener: () -> Unit,
        open val content: List<Content>,
        open val contentArrangement: ContentArrangement = ContentArrangement.HORIZONTAL,
        open val contentSpacing: Dp = 0.dp,
    ) : ButtonData(
        enabled = enabled,
        onClickListener = onClickListener,
    )

    data class Selectable(
        override val enabled: Boolean = true,
        override val onClickListener: () -> Unit,
        override val content: List<Content>,
        override val contentArrangement: ContentArrangement = ContentArrangement.HORIZONTAL,
        override val contentSpacing: Dp = 0.dp,
        val selected: Boolean,
    ) : Outlined(
        enabled = enabled,
        onClickListener = onClickListener,
        content,
        contentArrangement,
        contentSpacing,
    )

    data class Text(
        override val enabled: Boolean = true,
        override val onClickListener: () -> Unit,
        val content: Content.Text,
    ) : ButtonData(
        enabled = enabled,
        onClickListener = onClickListener,
    )

    sealed class Content {
        data class Text(val textWrapper: TextWrapper) : Content()

        data class Image(val imageWrapper: ImageWrapper) : Content()
    }

    enum class ContentArrangement {
        HORIZONTAL,
        VERTICAL,
    }
}
