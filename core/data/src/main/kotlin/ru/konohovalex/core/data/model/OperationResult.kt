package ru.konohovalex.core.data.model

sealed class OperationResult<D> {
    data class Success<D>(val data: D) : OperationResult<D>()

    data class Error<D>(val throwable: Throwable) : OperationResult<D>()
}
