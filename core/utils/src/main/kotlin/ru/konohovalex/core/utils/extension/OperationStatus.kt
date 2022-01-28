package ru.konohovalex.core.utils.extension

import ru.konohovalex.core.utils.model.MergedOperationStatus2
import ru.konohovalex.core.utils.model.OperationStatus

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
