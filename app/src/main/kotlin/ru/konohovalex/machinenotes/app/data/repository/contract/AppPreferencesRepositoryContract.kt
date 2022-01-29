package ru.konohovalex.machinenotes.app.data.repository.contract

interface AppPreferencesRepositoryContract {
    suspend fun getIsFirstLaunch(): Boolean
    suspend fun updateIsFirstLaunch(isFirstLaunch: Boolean): Boolean
}
