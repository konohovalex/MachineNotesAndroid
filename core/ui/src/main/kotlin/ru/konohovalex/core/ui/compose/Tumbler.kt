package ru.konohovalex.core.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.konohovalex.core.design.Theme
import ru.konohovalex.core.design.utils.buttonPaddingsAsPaddingValues
import ru.konohovalex.core.ui.R
import ru.konohovalex.core.ui.compose.model.ImageWrapper
import ru.konohovalex.core.ui.compose.model.Position
import ru.konohovalex.core.ui.compose.model.TextWrapper

@Composable
fun Tumbler(
    positions: List<Position>,
    selectedPositionIdState: State<String>,
    onSelectedPositionChanged: (String) -> Unit,
) {
    ThemedCard(shape = Theme.shapes.large) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min),
        ) {
            positions.forEachIndexed { index, position ->
                TumblerPosition(
                    position = position,
                    selectedPositionIdState = selectedPositionIdState,
                    onSelectedPositionChanged = onSelectedPositionChanged,
                )

                val shouldDrawDivider = index < positions.size - 1 && positions.isNotEmpty()
                if (shouldDrawDivider) {
                    Divider(
                        color = Theme.palette.accentColor,
                        modifier = Modifier
                            .width(1.dp)
                            .fillMaxHeight(),
                    )
                }
            }
        }
    }
}

@Composable
private fun TumblerPosition(
    position: Position,
    selectedPositionIdState: State<String>,
    onSelectedPositionChanged: (String) -> Unit,
) {
    val selectedPositionId by selectedPositionIdState
    val id = position.id
    val backgroundColor =
        if (selectedPositionId == id) Theme.palette.fillEnabledColor
        else Theme.palette.backgroundColor

    Box(
        modifier = Modifier
            .clickable {
                onSelectedPositionChanged.invoke(id)
            }
    ) {
        val baseModifier = Modifier
            .background(color = backgroundColor)
            .padding(Theme.paddings.buttonPaddingsAsPaddingValues())
            .size(24.dp)

        when (position) {
            is Position.Text -> TextTumblerPosition(
                position = position,
                modifier = baseModifier,
            )
            is Position.Image -> ImageTumblerPosition(
                position = position,
                modifier = baseModifier,
            )
        }
    }
}

@Composable
private fun TextTumblerPosition(
    position: Position.Text,
    modifier: Modifier,
) = with(position) {
    ThemedText(
        textWrapper = textWrapper,
        modifier = modifier,
        themedTextType = ThemedTextType.BUTTON,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun ImageTumblerPosition(
    position: Position.Image,
    modifier: Modifier,
) = with(position) {
    ThemedImage(
        imageWrapper = imageWrapper,
        modifier = modifier,
    )
}

@Preview
@Composable
private fun TumblersPreview() {
    val selectedPositionIdState = derivedStateOf { "0" }

    Theme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp),
        ) {
            Tumbler(
                positions = listOf(
                    Position.Text(
                        id = "0",
                        textWrapper = TextWrapper.PlainText(value = "Eng"),
                    ),
                    Position.Text(
                        id = "1",
                        textWrapper = TextWrapper.PlainText(value = "Рус"),
                    ),
                ),
                selectedPositionIdState = selectedPositionIdState,
                onSelectedPositionChanged = {},
            )

            Tumbler(
                positions = listOf(
                    Position.Image(
                        id = "0",
                        imageWrapper = ImageWrapper.ImageResource(resourceId = R.drawable.ic_light_mode),
                    ),
                    Position.Image(
                        id = "1",
                        imageWrapper = ImageWrapper.ImageResource(resourceId = R.drawable.ic_dark_mode),
                    ),
                ),
                selectedPositionIdState = selectedPositionIdState,
                onSelectedPositionChanged = {},
            )
        }
    }
}
