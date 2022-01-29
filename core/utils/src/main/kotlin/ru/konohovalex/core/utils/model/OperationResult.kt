package ru.konohovalex.core.utils.model

// tbd deprecated by Occam's Razor
sealed class OperationResult<D> {
    data class Success<D>(val data: D) : OperationResult<D>()

    data class Error<D>(val throwable: Throwable) : OperationResult<D>()
}
