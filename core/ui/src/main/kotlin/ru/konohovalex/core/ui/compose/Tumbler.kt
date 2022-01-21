package ru.konohovalex.core.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
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
import ru.konohovalex.core.ui.compose.model.TumblerData

/** To use enums or classes, which instances can be unambiguously compared, as [D]
 * is a recommended solution, as the selected one of [tumblerData]'s [TumblerData.positions]
 * will be determined by comparison with [selectedPositionDataState]'s value */
@Composable
fun <D> Tumbler(
    tumblerData: TumblerData<D>,
    modifier: Modifier = Modifier,
    actionsEnabledState: State<Boolean>,
    selectedPositionDataState: State<D?>,
    onSelectedPositionChanged: (positionData: D) -> Unit,
) {
    with(tumblerData) {
        Row(
            modifier = modifier
                .padding(Theme.paddings.contentDefault),
            horizontalArrangement = Arrangement.spacedBy(Theme.paddings.contentDefault),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TitleAndInfo(
                titleTextWrapper = titleTextWrapper,
                infoTextWrapper = infoTextWrapper,
            )

            PositionsBox(
                positions = positions,
                actionsEnabledState = actionsEnabledState,
                selectedPositionDataState = selectedPositionDataState,
                onSelectedPositionChanged = onSelectedPositionChanged,
            )
        }
    }
}

@Composable
private fun RowScope.TitleAndInfo(
    titleTextWrapper: TextWrapper?,
    infoTextWrapper: TextWrapper?,
) {
    if (titleTextWrapper != null || infoTextWrapper != null) {
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(Theme.paddings.contentDefault),
        ) {
            titleTextWrapper?.let {
                ThemedText(
                    themedTextType = ThemedTextType.BUTTON,
                    textWrapper = it,
                    modifier = Modifier
                        .fillMaxWidth(),
                )
            }
            infoTextWrapper?.let {
                ThemedText(
                    themedTextType = ThemedTextType.LABEL,
                    textWrapper = it,
                    modifier = Modifier
                        .fillMaxWidth(),
                )
            }
        }
    }
}

@Composable
private fun <D> PositionsBox(
    positions: List<Position<D>>,
    actionsEnabledState: State<Boolean>,
    selectedPositionDataState: State<D?>,
    onSelectedPositionChanged: (positionData: D) -> Unit,
) {
    ThemedCard(shape = Theme.shapes.large) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            PositionsRow(
                positions = positions,
                selectedPositionDataState = selectedPositionDataState,
                onSelectedPositionChanged = onSelectedPositionChanged,
            )

            val actionsEnabled by actionsEnabledState

            if (!actionsEnabled) {
                ThemedCircularProgressBar(
                    modifier = Modifier
                        .matchParentSize(),
                )
            }
        }
    }
}

@Composable
fun <D> PositionsRow(
    positions: List<Position<D>>,
    selectedPositionDataState: State<D?>,
    onSelectedPositionChanged: (positionData: D) -> Unit,
) {
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min),
    ) {
        positions.forEachIndexed { index, position ->
            TumblerPosition(
                position = position,
                selectedPositionDataState = selectedPositionDataState,
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

@Composable
private fun <D> TumblerPosition(
    position: Position<D>,
    selectedPositionDataState: State<D?>,
    onSelectedPositionChanged: (positionData: D) -> Unit,
) {
    val selectedPositionData by selectedPositionDataState
    val isSelectedPosition = selectedPositionData == position.data
    val backgroundColor =
        if (isSelectedPosition) Theme.palette.fillEnabledColor
        else Theme.palette.backgroundColor

    Box(
        modifier = Modifier
            .clickable {
                if (!isSelectedPosition) onSelectedPositionChanged.invoke(position.data)
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
private fun <D> TextTumblerPosition(
    position: Position.Text<D>,
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
private fun <D> ImageTumblerPosition(
    position: Position.Image<D>,
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
    val selectedPositionState = derivedStateOf { "0" }
    val actionsDisabledState = derivedStateOf { false }
    val actionsEnabledState = derivedStateOf { true }

    Theme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Tumbler(
                tumblerData = TumblerData(
                    titleTextWrapper = TextWrapper.PlainText(value = "Язык приложения"),
                    infoTextWrapper = TextWrapper.PlainText(value = "Распознавание речи будет доступно только языка приложения"),
                    positions = listOf(
                        Position.Text(
                            data = "0",
                            textWrapper = TextWrapper.PlainText(value = "Eng"),
                        ),
                        Position.Text(
                            data = "1",
                            textWrapper = TextWrapper.PlainText(value = "Рус"),
                        ),
                    ),
                ),
                actionsEnabledState = actionsDisabledState,
                selectedPositionDataState = selectedPositionState,
            ) {}

            Tumbler(
                tumblerData = TumblerData(
                    titleTextWrapper = TextWrapper.PlainText(value = "Тема (светлая|системная|тёмная)"),
                    positions = listOf(
                        Position.Image(
                            data = "0",
                            imageWrapper = ImageWrapper.ImageResource(resourceId = R.drawable.ic_light_mode),
                        ),
                        Position.Image(
                            data = "1",
                            imageWrapper = ImageWrapper.ImageResource(resourceId = R.drawable.ic_device),
                        ),
                        Position.Image(
                            data = "2",
                            imageWrapper = ImageWrapper.ImageResource(resourceId = R.drawable.ic_dark_mode),
                        ),
                    ),
                ),
                actionsEnabledState = actionsEnabledState,
                selectedPositionDataState = selectedPositionState,
            ) {}
        }
    }
}
