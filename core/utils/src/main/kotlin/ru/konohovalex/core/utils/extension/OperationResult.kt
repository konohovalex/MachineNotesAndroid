package ru.konohovalex.core.utils.extension

import ru.konohovalex.core.utils.model.OperationResult

// tbd deprecated by Occam's Razor
inline fun <reified D> OperationResult<D>.unwrap() = when (this) {
    is OperationResult.Success -> data
    is OperationResult.Error -> throw throwable
}
