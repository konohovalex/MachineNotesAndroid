package ru.konohovalex.core.utils.extension

import ru.konohovalex.core.utils.model.MergedOperationStatus2
import ru.konohovalex.core.utils.model.OperationStatus

fun <O> OperationStatus<O>.asError() = safeCast<OperationStatus.Error<O>>()

fun <O1, O2> OperationStatus.Plain<O1>.merge(
    other: OperationStatus.Plain<O2>,
): MergedOperationStatus2.Plain<O1, O2> = when {
    this is OperationStatus.Plain.Error || other is OperationStatus.Plain.Error ->
        MergedOperationStatus2.Plain.Error(this, other)
    this is OperationStatus.Plain.Processing && other is OperationStatus.Plain.Processing ->
        MergedOperationStatus2.Plain.Processing(this, other)
    this is OperationStatus.Plain.Completed && other is OperationStatus.Plain.Completed ->
        MergedOperationStatus2.Plain.Completed(this, other)
    else ->
        MergedOperationStatus2.Plain.Pending(this, other)
}

fun <I1, O1, O2> OperationStatus.WithInputData<I1, O1>.merge(
    other: OperationStatus.Plain<O2>,
): MergedOperationStatus2.WithInputData.Single<I1, O1, O2> = when {
    this is OperationStatus.WithInputData.Error || other is OperationStatus.Plain.Error ->
        MergedOperationStatus2.WithInputData.Single.Error(this, other)
    this is OperationStatus.WithInputData.Processing && other is OperationStatus.Plain.Processing ->
        MergedOperationStatus2.WithInputData.Single.Processing(this, other)
    this is OperationStatus.WithInputData.Completed && other is OperationStatus.Plain.Completed ->
        MergedOperationStatus2.WithInputData.Single.Completed(this, other)
    else ->
        MergedOperationStatus2.WithInputData.Single.Pending(this, other)
}

fun <I1, O1, I2, O2> OperationStatus.WithInputData<I1, O1>.merge(
    other: OperationStatus.WithInputData<I2, O2>,
): MergedOperationStatus2.WithInputData.Both<I1, O1, I2, O2> = when {
    this is OperationStatus.WithInputData.Error || other is OperationStatus.WithInputData.Error ->
        MergedOperationStatus2.WithInputData.Both.Error(this, other)
    this is OperationStatus.WithInputData.Processing && other is OperationStatus.WithInputData.Processing ->
        MergedOperationStatus2.WithInputData.Both.Processing(this, other)
    this is OperationStatus.WithInputData.Completed && other is OperationStatus.WithInputData.Completed ->
        MergedOperationStatus2.WithInputData.Both.Completed(this, other)
    else ->
        MergedOperationStatus2.WithInputData.Both.Pending(this, other)
}

fun <O1, O2> OperationStatus.Plain<O1>.map(
    mapper: (O1) -> O2,
): OperationStatus.Plain<O2> =
    try {
        when (this) {
            is OperationStatus.Plain.Pending ->
                OperationStatus.Plain.Pending()
            is OperationStatus.Plain.Processing ->
                OperationStatus.Plain.Processing()
            is OperationStatus.Plain.Completed ->
                OperationStatus.Plain.Completed(mapper.invoke(outputData))
            is OperationStatus.Plain.Error ->
                OperationStatus.Plain.Error(throwable)
        }
    }
    catch (throwable: Throwable) {
        OperationStatus.Plain.Error(throwable)
    }

fun <O1, O2> OperationStatus.Plain<O1?>.mapNullable(
    mapper: (O1) -> O2,
): OperationStatus.Plain<O2?> =
    try {
        when (this) {
            is OperationStatus.Plain.Pending ->
                OperationStatus.Plain.Pending()
            is OperationStatus.Plain.Processing ->
                OperationStatus.Plain.Processing()
            is OperationStatus.Plain.Completed ->
                OperationStatus.Plain.Completed(outputData?.let(mapper::invoke))
            is OperationStatus.Plain.Error ->
                OperationStatus.Plain.Error(throwable)
        }
    }
    catch (throwable: Throwable) {
        OperationStatus.Plain.Error(throwable)
    }

fun <I1, O1, I2, O2> OperationStatus.WithInputData<I1, O1>.map(
    inputDataMapper: (I1) -> I2,
    outputDataMapper: (O1) -> O2,
): OperationStatus.WithInputData<I2, O2> =
    inputDataMapper.invoke(inputData).let {
        try {
            when (this) {
                is OperationStatus.WithInputData.Pending ->
                    OperationStatus.WithInputData.Pending(it)
                is OperationStatus.WithInputData.Processing ->
                    OperationStatus.WithInputData.Processing(it)
                is OperationStatus.WithInputData.Completed ->
                    OperationStatus.WithInputData.Completed(it, outputDataMapper.invoke(outputData))
                is OperationStatus.WithInputData.Error ->
                    OperationStatus.WithInputData.Error(it, throwable)
            }
        }
        catch (throwable: Throwable) {
            OperationStatus.WithInputData.Error(it, throwable)
        }
    }
