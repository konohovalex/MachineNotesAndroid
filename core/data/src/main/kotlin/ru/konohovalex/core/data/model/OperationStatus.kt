package ru.konohovalex.core.data.model

sealed interface OperationStatus<O> {
    interface Pending<O> : OperationStatus<O>

    interface Processing<O> : OperationStatus<O>

    interface Completed<O> : OperationStatus<O> {
        val outputData: O
    }

    interface Error<O> : OperationStatus<O> {
        val throwable: Throwable
    }

    sealed class Plain<O> : OperationStatus<O> {
        class Pending<O> : Plain<O>(), OperationStatus.Pending<O>

        class Processing<O> : Plain<O>(), OperationStatus.Processing<O>

        data class Completed<O>(override val outputData: O) : Plain<O>(), OperationStatus.Completed<O>

        data class Error<O>(override val throwable: Throwable) : Plain<O>(), OperationStatus.Error<O>
    }

    sealed class WithInputData<I, O> : OperationStatus<O> {
        abstract val inputData: I

        data class Pending<I, O>(
            override val inputData: I,
        ) : WithInputData<I, O>(), OperationStatus.Pending<O>

        data class Processing<I, O>(
            override val inputData: I,
        ) : WithInputData<I, O>(), OperationStatus.Processing<O>

        data class Completed<I, O>(
            override val inputData: I,
            override val outputData: O,
        ) : WithInputData<I, O>(), OperationStatus.Completed<O>

        data class Error<I, O>(
            override val inputData: I,
            override val throwable: Throwable,
        ) : WithInputData<I, O>(), OperationStatus.Error<O>
    }
}
