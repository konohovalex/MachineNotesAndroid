package ru.konohovalex.core.ui.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import ru.konohovalex.core.design.extension.buttonPaddingsAsPaddingValues
import ru.konohovalex.core.design.model.Theme
import ru.konohovalex.core.ui.R
import ru.konohovalex.core.ui.model.ButtonData
import ru.konohovalex.core.ui.model.ImageWrapper
import ru.konohovalex.core.ui.model.TextWrapper

@Composable
fun ThemedButton(
    buttonData: ButtonData,
    modifier: Modifier = Modifier,
) {
    val onClickState = remember {
        mutableStateOf(buttonData.onClick)
    }
    onClickState.value = buttonData.onClick

    val enabledState = remember {
        mutableStateOf(buttonData.enabled)
    }
    enabledState.value = buttonData.enabled

    val fillEnabledColorState = remember {
        mutableStateOf(buttonData.fillEnabledColor)
    }
    fillEnabledColorState.value = buttonData.fillEnabledColor

    when (buttonData) {
        is ButtonData.WithContent -> ButtonWithContent(
            buttonData = buttonData,
            onClickState = onClickState,
            enabledState = enabledState,
            fillEnabledColorState = fillEnabledColorState,
            modifier = modifier,
        )
        is ButtonData.Text -> TextButton(
            buttonData = buttonData,
            onClickState = onClickState,
            enabledState = enabledState,
            modifier = modifier,
        )
    }
}

@Composable
private fun ButtonWithContent(
    buttonData: ButtonData.WithContent,
    onClickState: State<() -> Unit>,
    enabledState: State<Boolean>,
    fillEnabledColorState: State<Color?>,
    modifier: Modifier,
) {
    with(buttonData) {
        val contentState = remember {
            mutableStateOf(content)
        }
        contentState.value = content

        val contentArrangementState = remember {
            mutableStateOf(contentArrangement)
        }
        contentArrangementState.value = contentArrangement

        val contentSpacingState = remember {
            mutableStateOf(contentSpacing)
        }
        contentSpacingState.value = contentSpacing

        val backgroundColorState = remember {
            mutableStateOf(Color.Transparent)
        }

        when (buttonData) {
            is ButtonData.Regular -> {
                backgroundColorState.value = fillEnabledColorState.value ?: Theme.palette.fillEnabledColor

                RegularButton(
                    onClickState = onClickState,
                    enabledState = enabledState,
                    backgroundColorState = backgroundColorState,
                    contentState = contentState,
                    contentArrangementState = contentArrangementState,
                    contentSpacingState = contentSpacingState,
                    modifier = modifier,
                )
            }
            is ButtonData.Outlined -> {
                backgroundColorState.value =
                    if (buttonData.selected) fillEnabledColorState.value ?: Theme.palette.fillEnabledColor
                    else Theme.palette.backgroundColor

                OutlinedButton(
                    onClickState = onClickState,
                    enabledState = enabledState,
                    backgroundColorState = backgroundColorState,
                    contentState = contentState,
                    contentArrangementState = contentArrangementState,
                    contentSpacingState = contentSpacingState,
                    modifier = modifier,
                )
            }
        }
    }
}

@Composable
private fun RegularButton(
    onClickState: State<() -> Unit>,
    enabledState: State<Boolean>,
    backgroundColorState: State<Color>,
    contentState: State<List<ButtonData.Content>>,
    contentArrangementState: State<ButtonData.ContentArrangement>,
    contentSpacingState: State<Dp>,
    modifier: Modifier,
) {
    Button(
        onClick = onClickState.value,
        modifier = modifier,
        enabled = enabledState.value,
        shape = Theme.shapes.large,
        contentPadding = Theme.paddings.buttonPaddingsAsPaddingValues(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColorState.value,
            disabledBackgroundColor = Theme.palette.fillDisabledColor,
        ),
    ) {
        Content(
            contentState = contentState,
            contentArrangementState = contentArrangementState,
            contentSpacingState = contentSpacingState,
        )
    }
}

@Composable
private fun OutlinedButton(
    onClickState: State<() -> Unit>,
    enabledState: State<Boolean>,
    backgroundColorState: State<Color>,
    contentState: State<List<ButtonData.Content>>,
    contentArrangementState: State<ButtonData.ContentArrangement>,
    contentSpacingState: State<Dp>,
    modifier: Modifier,
) {
    OutlinedButton(
        onClick = onClickState.value,
        modifier = modifier,
        enabled = enabledState.value,
        shape = Theme.shapes.large,
        contentPadding = Theme.paddings.buttonPaddingsAsPaddingValues(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColorState.value,
            disabledBackgroundColor = Theme.palette.fillDisabledColor,
        ),
        border = BorderStroke(
            width = Theme.sizes.border,
            color = Theme.palette.accentColor,
        ),
    ) {
        Content(
            contentState = contentState,
            contentArrangementState = contentArrangementState,
            contentSpacingState = contentSpacingState,
        )
    }
}

@Composable
private fun Content(
    contentState: State<List<ButtonData.Content>>,
    contentArrangementState: State<ButtonData.ContentArrangement>,
    contentSpacingState: State<Dp>,
) {
    when (contentArrangementState.value) {
        ButtonData.ContentArrangement.HORIZONTAL -> Row(
            horizontalArrangement = Arrangement.spacedBy(contentSpacingState.value),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            contentState.value.Compose()
        }
        ButtonData.ContentArrangement.VERTICAL -> Column(
            verticalArrangement = Arrangement.spacedBy(contentSpacingState.value),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            contentState.value.Compose()
        }
    }
}

@Composable
private fun List<ButtonData.Content>.Compose() = forEach {
    when (it) {
        is ButtonData.Content.Text -> TextContent(
            content = it,
            textColor = it.textColor,
        )
        is ButtonData.Content.Image -> ImageContent(
            content = it,
        )
    }
}

@Composable
private fun TextContent(
    content: ButtonData.Content.Text,
    textColor: Color?,
) = ThemedText(
    textWrapper = content.textWrapper,
    themedTextType = ThemedTextType.BUTTON,
    textColor = textColor,
)

@Composable
private fun ImageContent(content: ButtonData.Content.Image) = ThemedImage(
    imageWrapper = content.imageWrapper,
    modifier = Modifier
        .size(Theme.sizes.icon),
)

@Composable
private fun TextButton(
    buttonData: ButtonData.Text,
    onClickState: State<() -> Unit>,
    enabledState: State<Boolean>,
    modifier: Modifier,
) {
    with(buttonData) {
        val contentState = remember {
            mutableStateOf(content)
        }
        contentState.value = content

        val textColorState = remember {
            mutableStateOf(content.textColor)
        }
        textColorState.value = content.textColor

        TextButton(
            onClick = onClickState.value,
            modifier = modifier,
            contentPadding = Theme.paddings.buttonPaddingsAsPaddingValues(),
            enabled = enabledState.value,
        ) {
            TextContent(
                content = contentState.value,
                textColor = textColorState.value,
            )
        }
    }
}

@Preview
@Composable
private fun ThemedButtonsPreview() {
    Theme(darkTheme = true) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Theme.paddings.contentExtraLarge),
        ) {
            val imageWrapper = ImageWrapper.ImageResource(resourceId = R.drawable.ic_notes)
            val textWrapper = TextWrapper.PlainText(value = "Редактировать")

            ThemedButton(
                ButtonData.Regular(
                    enabled = false,
                    onClick = {},
                    content = listOf(
                        ButtonData.Content.Image(imageWrapper = imageWrapper)
                    ),
                )
            )

            ThemedButton(
                ButtonData.Outlined(
                    onClick = {},
                    content = listOf(
                        ButtonData.Content.Image(imageWrapper = imageWrapper)
                    ),
                    selected = true,
                )
            )

            ThemedButton(
                ButtonData.Outlined(
                    onClick = {},
                    content = listOf(
                        ButtonData.Content.Image(imageWrapper = imageWrapper)
                    ),
                    selected = false,
                )
            )

            ThemedButton(
                ButtonData.Regular(
                    onClick = {},
                    fillEnabledColor = Theme.palette.errorColor,
                    content = listOf(
                        ButtonData.Content.Text(textWrapper = textWrapper),
                        ButtonData.Content.Image(imageWrapper = imageWrapper),
                    ),
                    contentSpacing = Theme.paddings.contentSmall,
                )
            )

            ThemedButton(
                ButtonData.Text(
                    onClick = {},
                    content = ButtonData.Content.Text(textWrapper = textWrapper),
                )
            )
        }
    }
}
