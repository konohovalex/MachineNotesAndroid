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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import ru.konohovalex.core.design.extension.buttonPaddingsAsPaddingValues
import ru.konohovalex.core.design.model.Theme
import ru.konohovalex.core.ui.R
import ru.konohovalex.core.ui.model.ImageWrapper
import ru.konohovalex.core.ui.model.Position
import ru.konohovalex.core.ui.model.TextWrapper
import ru.konohovalex.core.ui.model.TumblerData

/** To use enums or classes, which instances can be unambiguously compared, as [D]
 * is a recommended solution, as the selected one of [tumblerData]'s [TumblerData.positions]
 * will be determined by comparison with [selectedPositionData] */
@Composable
fun <D> Tumbler(
    tumblerData: TumblerData<D>,
    selectedPositionData: D?,
    actionsEnabled: Boolean,
    modifier: Modifier = Modifier,
    onSelectedPositionChanged: (positionData: D) -> Unit,
) {
    val titleTextWrapperState = remember {
        mutableStateOf(tumblerData.titleTextWrapper)
    }
    titleTextWrapperState.value = tumblerData.titleTextWrapper

    val infoTextWrapperState = remember {
        mutableStateOf(tumblerData.infoTextWrapper)
    }
    infoTextWrapperState.value = tumblerData.infoTextWrapper

    val positionsState = remember {
        mutableStateOf(tumblerData.positions)
    }
    positionsState.value = tumblerData.positions

    val selectedPositionDataState = remember {
        mutableStateOf(selectedPositionData)
    }
    selectedPositionDataState.value = selectedPositionData

    val actionsEnabledState = remember {
        mutableStateOf(true)
    }
    actionsEnabledState.value = actionsEnabled

    Row(
        modifier = modifier
            .padding(Theme.paddings.contentSmall),
        horizontalArrangement = Arrangement.spacedBy(Theme.paddings.contentSmall),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TitleAndInfo(
            titleTextWrapperState = titleTextWrapperState,
            infoTextWrapperState = infoTextWrapperState,
        )

        PositionsBox(
            positionsState = positionsState,
            selectedPositionDataState = selectedPositionDataState,
            actionsEnabledState = actionsEnabledState,
            onSelectedPositionChanged = onSelectedPositionChanged,
        )
    }
}

@Composable
private fun RowScope.TitleAndInfo(
    titleTextWrapperState: State<TextWrapper?>,
    infoTextWrapperState: State<TextWrapper?>,
) {
    val titleTextWrapper = titleTextWrapperState.value
    val infoTextWrapper = infoTextWrapperState.value

    if (titleTextWrapperState.value != null || infoTextWrapperState.value != null) {
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(Theme.paddings.contentSmall),
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
    positionsState: State<List<Position<D>>>,
    selectedPositionDataState: State<D?>,
    actionsEnabledState: State<Boolean>,
    onSelectedPositionChanged: (positionData: D) -> Unit,
) {
    ThemedCard(shape = Theme.shapes.large) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            PositionsRow(
                positionsState = positionsState,
                selectedPositionDataState = selectedPositionDataState,
                onSelectedPositionChanged = onSelectedPositionChanged,
            )

            if (!actionsEnabledState.value) {
                ThemedCircularProgressBar(
                    modifier = Modifier
                        .matchParentSize(),
                )
            }
        }
    }
}

@Composable
private fun <D> PositionsRow(
    positionsState: State<List<Position<D>>>,
    selectedPositionDataState: State<D?>,
    onSelectedPositionChanged: (positionData: D) -> Unit,
) {
    val positions = positionsState.value

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
                        .width(Theme.sizes.border)
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
    val isSelectedPosition = selectedPositionDataState.value == position.data
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
            .size(Theme.sizes.icon)

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
                selectedPositionData = "0",
                actionsEnabled = false,
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
                selectedPositionData = "1",
                actionsEnabled = true,
            ) {}
        }
    }
}
