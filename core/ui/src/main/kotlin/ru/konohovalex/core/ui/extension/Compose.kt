package ru.konohovalex.core.ui.extension

import androidx.compose.runtime.Composable

@Composable
inline fun <T, R> T.letCompose(crossinline block: (T) -> R): @Composable (() -> Unit) = {
    let {
        block.invoke(it)
    }
}
