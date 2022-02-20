package ru.konohovalex.machinenotes.app.data.repository.impl

import ru.konohovalex.core.utils.extension.withIo
import ru.konohovalex.machinenotes.app.data.repository.contract.AppPreferencesRepositoryContract
import ru.konohovalex.machinenotes.app.data.source.contract.AppPreferencesStorageContract
import javax.inject.Inject

class AppPreferencesRepositoryImpl
@Inject constructor(
    private val appPreferencesStorage: AppPreferencesStorageContract,
) : AppPreferencesRepositoryContract {
    // TODO("should there be safe update ops?")
    override suspend fun getIsFirstLaunch(): Boolean = withIo {
        appPreferencesStorage.getIsFirstLaunch()
    }

    override suspend fun updateIsFirstLaunch(isFirstLaunch: Boolean): Boolean = withIo {
        appPreferencesStorage.updateIsFirstLaunch(isFirstLaunch)
    }
}
