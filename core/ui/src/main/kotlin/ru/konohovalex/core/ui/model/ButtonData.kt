package ru.konohovalex.core.ui.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

sealed class ButtonData(
    open val enabled: Boolean = true,
    open val fillEnabledColor: Color? = null,
    open val onClick: () -> Unit,
) {
    sealed class WithContent(
        override val enabled: Boolean = true,
        override val fillEnabledColor: Color? = null,
        override val onClick: () -> Unit,
    ) : ButtonData(
        enabled = enabled,
        onClick = onClick,
        fillEnabledColor = fillEnabledColor,
    ) {
        abstract val content: List<Content>
        abstract val contentArrangement: ContentArrangement
        abstract val contentSpacing: Dp
    }

    data class Regular(
        override val enabled: Boolean = true,
        override val fillEnabledColor: Color? = null,
        override val onClick: () -> Unit,
        override val content: List<Content>,
        override val contentArrangement: ContentArrangement = ContentArrangement.HORIZONTAL,
        override val contentSpacing: Dp = 0.dp,
    ) : WithContent(
        enabled = enabled,
        onClick = onClick,
        fillEnabledColor = fillEnabledColor,
    )

    open class Outlined(
        override val enabled: Boolean = true,
        override val fillEnabledColor: Color? = null,
        override val onClick: () -> Unit,
        override val content: List<Content>,
        override val contentArrangement: ContentArrangement = ContentArrangement.HORIZONTAL,
        override val contentSpacing: Dp = 0.dp,
        val selected: Boolean = true,
    ) : WithContent(
        enabled = enabled,
        onClick = onClick,
        fillEnabledColor = fillEnabledColor,
    )

    data class Text(
        override val enabled: Boolean = true,
        override val onClick: () -> Unit,
        val content: Content.Text,
    ) : ButtonData(
        enabled = enabled,
        onClick = onClick,
    )

    sealed class Content {
        data class Text(
            val textWrapper: TextWrapper,
            val textColor: Color? = null,
        ) : Content()

        data class Image(
            val imageWrapper: ImageWrapper,
        ) : Content()
    }

    enum class ContentArrangement {
        HORIZONTAL,
        VERTICAL,
    }
}
