package ru.konohovalex.core.ui.compose.utils

import androidx.compose.runtime.Composable

@Composable
inline fun <T, R> T.letCompose(crossinline block: (T) -> R): @Composable (() -> Unit) = {
    let {
        block.invoke(it)
    }
}
