package ru.konohovalex.core.ui.compose

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import ru.konohovalex.core.design.Theme
import ru.konohovalex.core.ui.compose.model.TextWrapper
import ru.konohovalex.core.ui.compose.utils.unwrap

@Composable
fun ThemedText(
    themedTextType: ThemedTextType,
    textWrapper: TextWrapper,
    modifier: Modifier = Modifier,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
) {
    val color: Color
    val textStyle: TextStyle

    with(Theme.palette) {
        when (themedTextType) {
            ThemedTextType.HEADLINE -> {
                color = titleColor
                textStyle = Theme.typography.headline
            }
            ThemedTextType.TITLE -> {
                color = titleColor
                textStyle = Theme.typography.title
            }
            ThemedTextType.BODY -> {
                color = bodyColor
                textStyle = Theme.typography.body
            }
            ThemedTextType.LABEL -> {
                color = labelColor
                textStyle = Theme.typography.label
            }
            ThemedTextType.BUTTON -> {
                color = titleColor
                textStyle = Theme.typography.button
            }
        }
    }

    Text(
        text = textWrapper.unwrap(),
        modifier = modifier,
        color = color,
        fontSize = textStyle.fontSize,
        lineHeight = textStyle.lineHeight,
        fontWeight = textStyle.fontWeight,
        letterSpacing = textStyle.letterSpacing,
        textAlign = textAlign,
        overflow = overflow,
        maxLines = maxLines,
    )
}

enum class ThemedTextType {
    HEADLINE,
    TITLE,
    BODY,
    LABEL,
    BUTTON,
}
