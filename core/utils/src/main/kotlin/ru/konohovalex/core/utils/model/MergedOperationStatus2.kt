package ru.konohovalex.core.utils.model

sealed interface MergedOperationStatus2 {
    sealed class Plain<O1, O2> {
        abstract val first: OperationStatus<O1>
        abstract val second: OperationStatus<O2>

        data class Pending<O1, O2>(
            override val first: OperationStatus.Plain<O1>,
            override val second: OperationStatus.Plain<O2>,
        ) : MergedOperationStatus2.Plain<O1, O2>()

        data class Processing<O1, O2>(
            override val first: OperationStatus.Plain<O1>,
            override val second: OperationStatus.Plain<O2>,
        ) : MergedOperationStatus2.Plain<O1, O2>()

        data class Completed<O1, O2>(
            override val first: OperationStatus.Plain.Completed<O1>,
            override val second: OperationStatus.Plain.Completed<O2>,
        ) : MergedOperationStatus2.Plain<O1, O2>()

        data class Error<O1, O2>(
            override val first: OperationStatus.Plain<O1>,
            override val second: OperationStatus.Plain<O2>,
        ) : MergedOperationStatus2.Plain<O1, O2>()
    }

    sealed class WithInputData : MergedOperationStatus2 {
        sealed class Single<I1, O1, O2> : WithInputData() {
            abstract val first: OperationStatus.WithInputData<I1, O1>
            abstract val second: OperationStatus<O2>

            data class Pending<I1, O1, O2>(
                override val first: OperationStatus.WithInputData<I1, O1>,
                override val second: OperationStatus<O2>,
            ) : WithInputData.Single<I1, O1, O2>()

            data class Processing<I1, O1, O2>(
                override val first: OperationStatus.WithInputData<I1, O1>,
                override val second: OperationStatus<O2>,
            ) : WithInputData.Single<I1, O1, O2>()

            data class Completed<I1, O1, O2>(
                override val first: OperationStatus.WithInputData<I1, O1>,
                override val second: OperationStatus<O2>,
            ) : WithInputData.Single<I1, O1, O2>()

            data class Error<I1, O1, O2>(
                override val first: OperationStatus.WithInputData<I1, O1>,
                override val second: OperationStatus<O2>,
            ) : WithInputData.Single<I1, O1, O2>()
        }

        sealed class Both<I1, O1, I2, O2> : WithInputData() {
            abstract val first: OperationStatus.WithInputData<I1, O1>
            abstract val second: OperationStatus.WithInputData<I2, O2>

            data class Pending<I1, O1, I2, O2>(
                override val first: OperationStatus.WithInputData<I1, O1>,
                override val second: OperationStatus.WithInputData<I2, O2>,
            ) : WithInputData.Both<I1, O1, I2, O2>()

            data class Processing<I1, O1, I2, O2>(
                override val first: OperationStatus.WithInputData<I1, O1>,
                override val second: OperationStatus.WithInputData<I2, O2>,
            ) : WithInputData.Both<I1, O1, I2, O2>()

            data class Completed<I1, O1, I2, O2>(
                override val first: OperationStatus.WithInputData<I1, O1>,
                override val second: OperationStatus.WithInputData<I2, O2>,
            ) : WithInputData.Both<I1, O1, I2, O2>()

            data class Error<I1, O1, I2, O2>(
                override val first: OperationStatus.WithInputData<I1, O1>,
                override val second: OperationStatus.WithInputData<I2, O2>,
            ) : WithInputData.Both<I1, O1, I2, O2>()
        }
    }
}
