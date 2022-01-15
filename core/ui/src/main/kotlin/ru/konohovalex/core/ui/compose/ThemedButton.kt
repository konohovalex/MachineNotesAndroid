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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.konohovalex.core.design.Theme
import ru.konohovalex.core.design.utils.buttonPaddingsAsPaddingValues
import ru.konohovalex.core.ui.R
import ru.konohovalex.core.ui.compose.model.ButtonData
import ru.konohovalex.core.ui.compose.model.ImageWrapper
import ru.konohovalex.core.ui.compose.model.TextWrapper

@Composable
fun ThemedButton(
    buttonData: ButtonData,
    modifier: Modifier = Modifier,
) {
    when (buttonData) {
        is ButtonData.Regular -> RegularButton(
            buttonData = buttonData,
            modifier = modifier,
        )
        is ButtonData.Outlined -> OutlinedButton(
            buttonData = buttonData,
            modifier = modifier,
        )
        is ButtonData.Text -> TextButton(
            buttonData = buttonData,
            modifier = modifier,
        )
    }
}

@Composable
private fun RegularButton(
    buttonData: ButtonData.Regular,
    modifier: Modifier,
) = with(buttonData) {
    Button(
        onClick = onClickListener,
        modifier = modifier,
        enabled = enabled,
        shape = Theme.shapes.large,
        contentPadding = Theme.paddings.buttonPaddingsAsPaddingValues(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Theme.palette.fillEnabledColor,
            disabledBackgroundColor = Theme.palette.fillDisabledColor,
        ),
    ) {
        Content(
            content = content,
            contentArrangement = contentArrangement,
            contentSpacing = contentSpacing,
        )
    }
}

@Composable
private fun OutlinedButton(
    buttonData: ButtonData.Outlined,
    modifier: Modifier,
) = with(buttonData) {
    OutlinedButton(
        onClick = onClickListener,
        modifier = modifier,
        enabled = enabled,
        shape = Theme.shapes.large,
        contentPadding = Theme.paddings.buttonPaddingsAsPaddingValues(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor =
            if (this is ButtonData.Selectable)
                if (selected) Theme.palette.fillEnabledColor
                else Theme.palette.backgroundColor
            else Theme.palette.fillEnabledColor,
            disabledBackgroundColor = Theme.palette.fillDisabledColor,
        ),
        border = BorderStroke(
            width = 1.dp,
            color = Theme.palette.accentColor,
        ),
    ) {
        Content(
            content = content,
            contentArrangement = contentArrangement,
            contentSpacing = contentSpacing,
        )
    }
}

@Composable
private fun TextButton(
    buttonData: ButtonData.Text,
    modifier: Modifier,
) = with(buttonData) {
    TextButton(
        onClick = onClickListener,
        modifier = modifier,
        contentPadding = Theme.paddings.buttonPaddingsAsPaddingValues(),
        enabled = enabled,
    ) {
        TextContent(
            content = content,
        )
    }
}

@Composable
fun Content(
    content: List<ButtonData.Content>,
    contentArrangement: ButtonData.ContentArrangement,
    contentSpacing: Dp,
) {
    val contentAction: @Composable () -> Unit = {
        content.forEach {
            when (it) {
                is ButtonData.Content.Text -> TextContent(content = it)
                is ButtonData.Content.Image -> ImageContent(content = it)
            }
        }
    }

    when (contentArrangement) {
        ButtonData.ContentArrangement.HORIZONTAL -> Row(
            horizontalArrangement = Arrangement.spacedBy(contentSpacing),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            contentAction.invoke()
        }
        ButtonData.ContentArrangement.VERTICAL -> Column(
            verticalArrangement = Arrangement.spacedBy(contentSpacing),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            contentAction.invoke()
        }
    }
}

@Composable
fun TextContent(content: ButtonData.Content.Text) = ThemedText(
    textWrapper = content.textWrapper,
    themedTextType = ThemedTextType.BUTTON,
)

@Composable
fun ImageContent(content: ButtonData.Content.Image) = ThemedImage(
    imageWrapper = content.imageWrapper,
    modifier = Modifier
        .size(24.dp),
)

@Preview
@Composable
private fun ThemedButtonsPreview() {
    Theme(darkTheme = true) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp),
        ) {
            val imageWrapper = ImageWrapper.ImageResource(resourceId = R.drawable.ic_pensil)
            val textWrapper = TextWrapper.PlainText(value = "Редактировать")

            ThemedButton(
                ButtonData.Regular(
                    enabled = false,
                    content = listOf(
                        ButtonData.Content.Image(imageWrapper = imageWrapper)
                    ),
                    onClickListener = {},
                )
            )

            ThemedButton(
                ButtonData.Selectable(
                    content = listOf(
                        ButtonData.Content.Image(imageWrapper = imageWrapper)
                    ),
                    onClickListener = {},
                    selected = false,
                )
            )

            ThemedButton(
                ButtonData.Outlined(
                    content = listOf(
                        ButtonData.Content.Text(textWrapper = textWrapper),
                        ButtonData.Content.Image(imageWrapper = imageWrapper),
                    ),
                    onClickListener = {},
                    contentSpacing = Theme.paddings.contentDefault,
                )
            )

            ThemedButton(
                ButtonData.Text(
                    content = ButtonData.Content.Text(textWrapper = textWrapper),
                    onClickListener = {},
                )
            )
        }
    }
}
