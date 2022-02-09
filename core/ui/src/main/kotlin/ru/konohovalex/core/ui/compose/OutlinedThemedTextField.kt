package ru.konohovalex.core.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.konohovalex.core.design.model.Theme
import ru.konohovalex.core.ui.R
import ru.konohovalex.core.ui.extension.letCompose
import ru.konohovalex.core.ui.extension.unwrap
import ru.konohovalex.core.ui.model.ImageWrapper
import ru.konohovalex.core.ui.model.TextWrapper

@Composable
fun OutlinedThemedTextField(
    textState: MutableState<String>,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    labelTextWrapper: TextWrapper? = null,
    placeholderTextWrapper: TextWrapper? = null,
    leadingIconImageWrapper: ImageWrapper? = null,
    onLeadingIconClick: (() -> Unit)? = null,
    trailingIconImageWrapper: ImageWrapper? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    errorTextWrapper: TextWrapper? = null,
    singleLine: Boolean = true,
) {
    val isError = errorTextWrapper != null

    Column {
        OutlinedTextField(
            value = textState.value,
            onValueChange = {
                textState.value = it
                onValueChanged.invoke(it)
            },
            modifier = modifier,
            enabled = enabled,
            textStyle = Theme.typography.body,
            label = labelTextWrapper?.unwrapAndCompose(),
            placeholder = placeholderTextWrapper?.unwrapAndCompose(),
            leadingIcon = icon(leadingIconImageWrapper, onLeadingIconClick),
            trailingIcon = icon(trailingIconImageWrapper, onTrailingIconClick),
            isError = isError,
            singleLine = singleLine,
            shape = Theme.shapes.small,
            colors = colors(isError),
        )
        errorTextWrapper?.let {
            ErrorText(errorTextWrapper)
        }
    }
}

@Composable
private fun TextWrapper.unwrapAndCompose() = letCompose {
    Text(text = it.unwrap())
}

@Composable
private fun icon(
    imageWrapper: ImageWrapper?,
    onClick: (() -> Unit)?,
) = imageWrapper?.letCompose {
    val iconComposable: @Composable () -> Unit = {
        Icon(
            painter = it.unwrap(),
            contentDescription = it.contentDescription?.unwrap(),
        )
    }
    onClick?.let {
        IconButton(onClick = it) {
            iconComposable.invoke()
        }
    } ?: iconComposable.invoke()
}

@Composable
private fun colors(isError: Boolean) = TextFieldDefaults.outlinedTextFieldColors(
    textColor = Theme.palette.bodyColor,
    disabledTextColor = Theme.palette.fillDisabledColor,
    cursorColor = Theme.palette.accentColor,
    errorCursorColor = Theme.palette.errorColor,
    focusedBorderColor = Theme.palette.accentColor,
    unfocusedBorderColor = Theme.palette.accentColor,
    disabledBorderColor = Theme.palette.fillDisabledColor,
    errorBorderColor = Theme.palette.errorColor,
    leadingIconColor = Theme.palette.iconTintColor,
    disabledLeadingIconColor = Theme.palette.fillDisabledColor,
    errorLeadingIconColor = Theme.palette.iconTintColor,
    focusedLabelColor = Theme.palette.accentColor,
    unfocusedLabelColor = if (isError) Theme.palette.errorColor else Theme.palette.accentColor,
    disabledLabelColor = Theme.palette.fillDisabledColor,
    errorLabelColor = Theme.palette.errorColor,
    trailingIconColor = Theme.palette.iconTintColor,
    disabledTrailingIconColor = Theme.palette.fillDisabledColor,
    errorTrailingIconColor = Theme.palette.iconTintColor,
    placeholderColor = Theme.palette.labelColor,
    disabledPlaceholderColor = Theme.palette.labelColor,
)

@Composable
private fun ErrorText(errorTextWrapper: TextWrapper?) {
    ThemedText(
        themedTextType = ThemedTextType.LABEL,
        textWrapper = errorTextWrapper ?: TextWrapper.PlainText(value = ""),
        modifier = Modifier
            .padding(
                start = Theme.paddings.contentMedium,
                end = Theme.paddings.contentMedium,
            ),
        textColor = Theme.palette.errorColor,
    )
}

@Preview
@Composable
private fun ThemedTextFieldPreview() {
    val labelTextWrapper = TextWrapper.PlainText(value = "Label")
    val placeholderTextWrapper = TextWrapper.StringResource(resourceId = R.string.search)

    val iconImageWrapper = ImageWrapper.ImageResource(resourceId = R.drawable.ic_search)

    val errorTextWrapper = TextWrapper.PlainText(value = "ОшибкаОшибкаОшибкаОшибкаОшибкаОшибка")

    Theme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Theme.paddings.contentMax),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Theme.paddings.contentExtraLarge),
        ) {
            OutlinedThemedTextField(
                textState = mutableStateOf("Text"),
                onValueChanged = {},
                labelTextWrapper = labelTextWrapper,
                placeholderTextWrapper = placeholderTextWrapper,
                leadingIconImageWrapper = iconImageWrapper,
                errorTextWrapper = errorTextWrapper,
            )

            OutlinedThemedTextField(
                textState = mutableStateOf("Text"),
                onValueChanged = {},
                labelTextWrapper = labelTextWrapper,
                placeholderTextWrapper = placeholderTextWrapper,
                leadingIconImageWrapper = iconImageWrapper,
            )

            OutlinedThemedTextField(
                textState = mutableStateOf(""),
                onValueChanged = {},
                labelTextWrapper = labelTextWrapper,
                placeholderTextWrapper = placeholderTextWrapper,
                trailingIconImageWrapper = iconImageWrapper,
                enabled = false,
            )
        }
    }
}
