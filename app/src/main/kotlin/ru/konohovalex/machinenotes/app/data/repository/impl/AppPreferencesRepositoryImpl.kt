package ru.konohovalex.machinenotes.app.data.repository.impl

import ru.konohovalex.core.utils.model.OperationResult
import ru.konohovalex.machinenotes.app.data.repository.contract.AppPreferencesRepositoryContract
import ru.konohovalex.machinenotes.app.data.source.contract.AppPreferencesStorageContract
import javax.inject.Inject

class AppPreferencesRepositoryImpl
@Inject constructor(
    private val appPreferencesStorage: AppPreferencesStorageContract,
) : AppPreferencesRepositoryContract {
    override suspend fun getIsFirstLaunch(): OperationResult<Boolean> {
        val isFirstLaunch = appPreferencesStorage.getIsFirstLaunch()
        return OperationResult.Success(isFirstLaunch)
    }

    override suspend fun updateIsFirstLaunch(isFirstLaunch: Boolean): OperationResult<Boolean> {
        val updatedIsFirstLaunch = appPreferencesStorage.updateIsFirstLaunch(isFirstLaunch)
        return OperationResult.Success(updatedIsFirstLaunch)
    }
}
