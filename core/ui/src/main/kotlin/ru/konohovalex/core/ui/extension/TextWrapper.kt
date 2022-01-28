package ru.konohovalex.core.ui.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import ru.konohovalex.core.ui.model.TextWrapper

@Composable
fun TextWrapper.unwrap(): String = when (this) {
    is TextWrapper.StringResource -> stringResource(resourceId, *formatArgs)
    is TextWrapper.QuantityStringResource -> {
        /** Added to follow [androidx.compose.ui.res.resources] logic */
        LocalConfiguration.current

        val resources = LocalContext.current.resources

        resources.getQuantityString(resourceId, quantity, *formatArgs)
    }
    is TextWrapper.PlainText -> value
    is TextWrapper.Composite -> buildString {
        textWrappers.forEachIndexed { index, textWrapper ->
            if (index in 1 until textWrappers.size) divider?.let(::append)
            append(textWrapper.unwrap())
        }
    }
}
