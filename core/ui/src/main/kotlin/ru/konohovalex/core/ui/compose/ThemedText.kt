package ru.konohovalex.core.ui.compose

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import ru.konohovalex.core.design.Theme

@Composable
fun ThemedText(
    themedTextType: ThemedTextType,
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
) = Text(
    color = with(Theme.palette) {
        when (themedTextType) {
            ThemedTextType.HEADLINE -> titleColor
            ThemedTextType.TITLE -> titleColor
            ThemedTextType.BODY -> bodyColor
            ThemedTextType.LABEL -> labelColor
        }
    },
    fontSize = with(Theme.textStyles) {
        when (themedTextType) {
            ThemedTextType.HEADLINE -> headline
            ThemedTextType.TITLE -> title
            ThemedTextType.BODY -> body
            ThemedTextType.LABEL -> label
        }
    }.fontSize,
    fontWeight = with(Theme.textStyles) {
        when (themedTextType) {
            ThemedTextType.HEADLINE -> headline
            ThemedTextType.TITLE -> title
            ThemedTextType.BODY -> body
            ThemedTextType.LABEL -> label
        }
    }.fontWeight,
    text = text,
    modifier = modifier,
    textAlign = textAlign,
    overflow = overflow,
    maxLines = maxLines,
)

enum class ThemedTextType {
    HEADLINE,
    TITLE,
    BODY,
    LABEL,
}
