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
    with(buttonData) {
        when (this) {
            is ButtonData.WithContent -> ButtonWithContent(
                buttonData = this,
                modifier = modifier,
            )
            is ButtonData.Text -> TextButton(
                buttonData = this,
                modifier = modifier,
            )
        }
    }
}

@Composable
private fun ButtonWithContent(
    buttonData: ButtonData.WithContent,
    modifier: Modifier,
) {
    when (buttonData) {
        is ButtonData.Regular -> {
            RegularButton(
                buttonData = buttonData,
                modifier = modifier,
            )
        }
        is ButtonData.Outlined -> {
            OutlinedButton(
                buttonData = buttonData,
                modifier = modifier,
            )
        }
    }
}

@Composable
private fun RegularButton(
    buttonData: ButtonData.Regular,
    modifier: Modifier,
) {
    with(buttonData) {
        Button(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            shape = Theme.shapes.large,
            contentPadding = Theme.paddings.buttonPaddingsAsPaddingValues(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = fillEnabledColor ?: Theme.palette.fillEnabledColor,
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
}

@Composable
private fun OutlinedButton(
    buttonData: ButtonData.Outlined,
    modifier: Modifier,
) {
    with(buttonData) {
        OutlinedButton(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            shape = Theme.shapes.large,
            contentPadding = Theme.paddings.buttonPaddingsAsPaddingValues(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (selected) fillEnabledColor ?: Theme.palette.fillEnabledColor
                else Theme.palette.backgroundColor,
                disabledBackgroundColor = Theme.palette.fillDisabledColor,
            ),
            border = BorderStroke(
                width = Theme.sizes.border,
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
}

@Composable
private fun Content(
    content: List<ButtonData.Content>,
    contentArrangement: ButtonData.ContentArrangement,
    contentSpacing: Dp,
) {
    when (contentArrangement) {
        ButtonData.ContentArrangement.HORIZONTAL -> Row(
            horizontalArrangement = Arrangement.spacedBy(contentSpacing),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            content.Compose()
        }
        ButtonData.ContentArrangement.VERTICAL -> Column(
            verticalArrangement = Arrangement.spacedBy(contentSpacing),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            content.Compose()
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
    modifier: Modifier,
) {
    with(buttonData) {
        TextButton(
            onClick = onClick,
            modifier = modifier,
            contentPadding = Theme.paddings.buttonPaddingsAsPaddingValues(),
            enabled = enabled,
        ) {
            TextContent(
                content = content,
                textColor = content.textColor,
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
