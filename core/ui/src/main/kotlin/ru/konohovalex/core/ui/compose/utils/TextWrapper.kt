package ru.konohovalex.core.ui.compose.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.konohovalex.core.ui.compose.model.TextWrapper

@Composable
fun TextWrapper.unwrap() = when (this) {
    is TextWrapper.StringResource -> stringResource(resourceId)
    is TextWrapper.PlainText -> value
}
