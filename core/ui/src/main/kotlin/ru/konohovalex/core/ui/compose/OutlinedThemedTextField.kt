package ru.konohovalex.core.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.konohovalex.core.design.Theme
import ru.konohovalex.core.ui.R
import ru.konohovalex.core.ui.compose.model.ImageWrapper
import ru.konohovalex.core.ui.compose.model.TextWrapper
import ru.konohovalex.core.ui.compose.utils.letCompose
import ru.konohovalex.core.ui.compose.utils.unwrap

@Composable
fun OutlinedThemedTextField(
    text: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    labelTextWrapper: TextWrapper? = null,
    placeholderTextWrapper: TextWrapper? = null,
    leadingIconImageWrapper: ImageWrapper? = null,
    trailingIconImageWrapper: ImageWrapper? = null,
    enabledState: State<Boolean> = derivedStateOf { true },
    errorState: State<Boolean> = derivedStateOf { false },
) {
    val enabled by enabledState
    val isError by errorState

    val textState = remember {
        mutableStateOf(text)
    }

    OutlinedTextField(
        value = textState.value,
        onValueChange = {
            textState.value = it
            onValueChanged.invoke(it)
        },
        modifier = modifier,
        enabled = enabled,
        textStyle = Theme.typography.body,
        label = labelTextWrapper?.letCompose {
            Text(text = it.unwrap())
        },
        placeholder = placeholderTextWrapper?.letCompose {
            Text(text = it.unwrap())
        },
        leadingIcon = leadingIconImageWrapper?.letCompose {
            Icon(
                painter = it.unwrap(),
                contentDescription = it.contentDescription?.unwrap(),
            )
        },
        trailingIcon = trailingIconImageWrapper?.letCompose {
            Icon(
                painter = it.unwrap(),
                contentDescription = it.contentDescription?.unwrap(),
            )
        },
        isError = isError,
        singleLine = true,
        shape = Theme.shapes.small,
        colors = TextFieldDefaults.outlinedTextFieldColors(
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
            unfocusedLabelColor = Theme.palette.accentColor,
            disabledLabelColor = Theme.palette.fillDisabledColor,
            errorLabelColor = Theme.palette.errorColor,
            trailingIconColor = Theme.palette.iconTintColor,
            disabledTrailingIconColor = Theme.palette.fillDisabledColor,
            errorTrailingIconColor = Theme.palette.iconTintColor,
            placeholderColor = Theme.palette.labelColor,
            disabledPlaceholderColor = Theme.palette.labelColor,
        ),
    )
}

@Preview
@Composable
fun ThemedTextFieldPreview() {
    val labelTextWrapper = TextWrapper.PlainText(value = "Label")
    val placeholderTextWrapper = TextWrapper.StringResource(resourceId = R.string.search)

    val iconImageWrapper = ImageWrapper.ImageResource(resourceId = R.drawable.ic_search)

    val enabledState = derivedStateOf { true }
    val disabledState = derivedStateOf { false }

    val noErrorState = derivedStateOf { false }
    val errorState = derivedStateOf { true }

    Theme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp),
        ) {
            OutlinedThemedTextField(
                text = "Text",
                onValueChanged = {},
                labelTextWrapper = labelTextWrapper,
                placeholderTextWrapper = placeholderTextWrapper,
                leadingIconImageWrapper = iconImageWrapper,
                enabledState = enabledState,
                errorState = errorState,
            )

            OutlinedThemedTextField(
                text = "Text",
                onValueChanged = {},
                labelTextWrapper = labelTextWrapper,
                placeholderTextWrapper = placeholderTextWrapper,
                leadingIconImageWrapper = iconImageWrapper,
                enabledState = enabledState,
                errorState = noErrorState,
            )

            OutlinedThemedTextField(
                text = "",
                onValueChanged = {},
                labelTextWrapper = labelTextWrapper,
                placeholderTextWrapper = placeholderTextWrapper,
                trailingIconImageWrapper = iconImageWrapper,
                enabledState = disabledState,
                errorState = noErrorState,
            )
        }
    }
}
