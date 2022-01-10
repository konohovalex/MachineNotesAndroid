package ru.konohovalex.core.ui.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.konohovalex.core.design.Theme

@Composable
fun ThemedCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Card(
        modifier = modifier,
        elevation = 4.dp,
        shape = Theme.shapes.medium,
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
