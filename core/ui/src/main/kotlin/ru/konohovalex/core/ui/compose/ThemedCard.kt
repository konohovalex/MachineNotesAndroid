package ru.konohovalex.core.ui.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.konohovalex.core.design.Theme

@Composable
fun ThemedCard(
    modifier: Modifier = Modifier,
    shape: Shape = Theme.shapes.medium,
    content: @Composable () -> Unit,
) {
    Card(
        modifier = modifier,
        elevation = Theme.materialElevations.cardResting,
        shape = shape,
        backgroundColor = Theme.palette.backgroundColor,
        border = BorderStroke(
            width = 1.dp,
            color = Theme.palette.accentColor,
        ),
        content = content,
    )
}

@Preview
@Composable
private fun ThemedCardPreview() {
    Theme(darkTheme = true) {
        ThemedCard {
            Text(text = "ThemedCardPreview")
        }
    }
}
