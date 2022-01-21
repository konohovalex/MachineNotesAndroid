package ru.konohovalex.core.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import ru.konohovalex.core.data.model.MergedOperationStatus2
import ru.konohovalex.core.data.model.OperationStatus
import ru.konohovalex.core.data.utils.merge

@JvmName("combineToMergedOperationStatus2Plain")
fun <O1, O2> Flow<OperationStatus.Plain<O1>>.combineToMergedOperationStatus2(
    flow: Flow<OperationStatus.Plain<O2>>,
): Flow<MergedOperationStatus2.Plain<O1, O2>> = combine(flow) { operationStatus1, operationStatus2 ->
    operationStatus1.merge(operationStatus2)
}

@JvmName("combineToMergedOperationStatus2WithInputSingle")
fun <I1, O1, O2> Flow<OperationStatus.WithInputData<I1, O1>>.combineToMergedOperationStatus2(
    flow: Flow<OperationStatus.Plain<O2>>,
): Flow<MergedOperationStatus2.WithInputData.Single<I1, O1, O2>> = combine(flow) { operationStatus1, operationStatus2 ->
    operationStatus1.merge(operationStatus2)
}

@JvmName("combineToMergedOperationStatus2WithInputBoth")
fun <I1, O1, I2, O2> Flow<OperationStatus.WithInputData<I1, O1>>.combineToMergedOperationStatus2(
    flow: Flow<OperationStatus.WithInputData<I2, O2>>,
): Flow<MergedOperationStatus2.WithInputData.Both<I1, O1, I2, O2>> = combine(flow) { operationStatus1, operationStatus2 ->
    operationStatus1.merge(operationStatus2)
}
