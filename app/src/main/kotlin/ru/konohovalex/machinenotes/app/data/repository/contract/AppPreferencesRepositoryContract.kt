package ru.konohovalex.machinenotes.app.data.repository.contract

import ru.konohovalex.core.utils.model.OperationResult

interface AppPreferencesRepositoryContract {
    suspend fun getIsFirstLaunch(): OperationResult<Boolean>
    suspend fun updateIsFirstLaunch(isFirstLaunch: Boolean): OperationResult<Boolean>
}
