package ru.konohovalex.core.data.utils

import ru.konohovalex.core.data.model.OperationResult

inline fun <reified D> OperationResult<D>.unwrap() = when (this) {
    is OperationResult.Success -> data
    is OperationResult.Error -> throw throwable
}
