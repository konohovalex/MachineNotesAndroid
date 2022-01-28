package ru.konohovalex.core.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.konohovalex.core.design.model.Theme

@Composable
fun ThemedCircularProgressBar(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clickable(false) {},
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center),
            color = Theme.palette.accentColor,
        )
    }
}

@Preview(
    widthDp = 200,
    heightDp = 200,
)
@Composable
private fun ThemedCircularProgressBarPreview() {
    Theme(darkTheme = true) {
        ThemedCircularProgressBar(
            modifier = Modifier
                .fillMaxSize(),
        )
    }
}
