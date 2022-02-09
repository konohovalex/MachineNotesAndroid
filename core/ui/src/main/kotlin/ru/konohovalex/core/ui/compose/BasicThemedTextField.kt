package ru.konohovalex.core.ui.compose

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import ru.konohovalex.core.design.model.Theme

@Composable
fun BasicThemedTextField(
    textState: MutableState<String>,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    singleLine: Boolean = false,
) {
    BasicTextField(
        value = textState.value,
        onValueChange = {
            textState.value = it
            onValueChanged.invoke(it)
        },
        modifier = modifier,
        enabled = enabled,
        textStyle = Theme.typography.body.copy(
            color = Theme.palette.bodyColor,
        ),
        cursorBrush = SolidColor(Theme.palette.accentColor),
        singleLine = singleLine,
    )
}
