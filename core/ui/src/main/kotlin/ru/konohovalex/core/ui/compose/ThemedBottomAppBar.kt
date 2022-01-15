package ru.konohovalex.core.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.konohovalex.core.design.Theme
import ru.konohovalex.core.ui.R
import ru.konohovalex.core.ui.compose.model.ButtonData
import ru.konohovalex.core.ui.compose.model.ImageWrapper
import ru.konohovalex.core.ui.compose.model.Position
import ru.konohovalex.core.ui.compose.model.TextWrapper
import ru.konohovalex.core.ui.compose.utils.unwrap

@Composable
fun ThemedBottomAppBar(
    positions: List<Position.Image>,
    selectedPositionIdState: State<String>,
    onSelectedPositionChanged: (String) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = Theme.palette.backgroundColor,
        elevation = Theme.materialElevations.bottomNavigationBar,
    ) {
        Row(
            modifier = Modifier
                .padding(
                    PaddingValues(
                        horizontal = 0.dp,
                        vertical = 6.dp,
                    )
                ),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            positions.forEach {
                BottomAppBarPosition(
                    position = it,
                    selectedPositionIdState = selectedPositionIdState,
                    onSelectedPositionChanged = onSelectedPositionChanged,
                )
            }
        }
    }
}

@Composable
private fun BottomAppBarPosition(
    position: Position.Image,
    selectedPositionIdState: State<String>,
    onSelectedPositionChanged: (String) -> Unit,
) = with(position) {
    val selectedPositionId by selectedPositionIdState

    ThemedButton(
        buttonData = ButtonData.Selectable(
            onClickListener = {
                onSelectedPositionChanged.invoke(id)
            },
            content = mutableListOf<ButtonData.Content>(
                ButtonData.Content.Image(imageWrapper = imageWrapper)
            ).apply {
                val contentDescription = imageWrapper.contentDescription
                contentDescription
                    ?.unwrap()
                    ?.takeIf { it.isNotBlank() }
                    ?.let {
                        add(
                            ButtonData.Content.Text(textWrapper = contentDescription)
                        )
                    }
            },
            contentArrangement = ButtonData.ContentArrangement.VERTICAL,
            contentSpacing = 4.dp,
            selected = selectedPositionId == id,
        )
    )
}

@Preview
@Composable
private fun ThemedBottomAppBarPreview() {
    val selectedPositionIdState = derivedStateOf { "0" }

    Theme {
        ThemedBottomAppBar(
            positions = listOf(
                Position.Image(
                    id = "0",
                    imageWrapper = ImageWrapper.ImageResource(resourceId = R.drawable.ic_pensil),
                ),
                Position.Image(
                    id = "1",
                    imageWrapper = ImageWrapper.ImageResource(
                        resourceId = R.drawable.ic_preferences,
                        contentDescription = TextWrapper.PlainText(value = "Настройки"),
                    ),
                ),
            ),
            selectedPositionIdState = selectedPositionIdState,
            onSelectedPositionChanged = {},
        )
    }
}
