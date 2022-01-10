package ru.konohovalex.core.data.model

sealed class OperationStatus<I, O> {
    abstract val inputData: I

    data class Pending<I, O>(
        override val inputData: I,
    ) : OperationStatus<I, O>()

    data class Processing<I, O>(
        override val inputData: I,
    ) : OperationStatus<I, O>()

    data class Completed<I, O>(
        override val inputData: I,
        val outputData: O,
    ) : OperationStatus<I, O>()

    data class Error<I, O>(
        override val inputData: I,
        val throwable: Throwable,
    ) : OperationStatus<I, O>()
}
