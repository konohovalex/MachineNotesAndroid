package ru.konohovalex.machinenotes.app.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.konohovalex.core.utils.extension.unwrap
import ru.konohovalex.core.utils.model.OperationStatus
import ru.konohovalex.machinenotes.app.data.repository.contract.AppPreferencesRepositoryContract
import javax.inject.Inject

class GetIsFirstLaunchUseCase
@Inject constructor(
    private val appPreferencesRepository: AppPreferencesRepositoryContract,
) {
    operator fun invoke(): Flow<OperationStatus.Plain<Boolean>> = flow {
        try {
            emit(OperationStatus.Plain.Pending())

            emit(OperationStatus.Plain.Processing())

            val isFirstLaunch = appPreferencesRepository.getIsFirstLaunch().unwrap()

            emit(OperationStatus.Plain.Completed(isFirstLaunch))
        }
        catch (throwable: Throwable) {
            emit(OperationStatus.Plain.Error(throwable))
        }
    }
}
