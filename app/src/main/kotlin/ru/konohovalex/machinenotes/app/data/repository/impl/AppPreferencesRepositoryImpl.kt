package ru.konohovalex.machinenotes.app.data.repository.impl

import ru.konohovalex.machinenotes.app.data.repository.contract.AppPreferencesRepositoryContract
import ru.konohovalex.machinenotes.app.data.source.contract.AppPreferencesStorageContract
import javax.inject.Inject

class AppPreferencesRepositoryImpl
@Inject constructor(
    private val appPreferencesStorage: AppPreferencesStorageContract,
) : AppPreferencesRepositoryContract {
    override suspend fun getIsFirstLaunch(): Boolean =
        appPreferencesStorage.getIsFirstLaunch()

    override suspend fun updateIsFirstLaunch(isFirstLaunch: Boolean): Boolean =
        appPreferencesStorage.updateIsFirstLaunch(isFirstLaunch)
}
