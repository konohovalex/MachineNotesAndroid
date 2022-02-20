package ru.konohovalex.machinenotes.app.data.source.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.firstOrNull
import ru.konohovalex.machinenotes.app.data.source.contract.AppPreferencesStorageContract
import javax.inject.Inject

class AppPreferencesStorageImpl
@Inject constructor(
    private val preferencesDataStore: DataStore<Preferences>,
) : AppPreferencesStorageContract {
    companion object {
        private val KEY_IS_FIRST_LAUNCH = booleanPreferencesKey(name = "key_is_first_launch")
    }

    override suspend fun getIsFirstLaunch(): Boolean =
        preferencesDataStore
            .data
            .firstOrNull()
            ?.get(KEY_IS_FIRST_LAUNCH)
            ?: true

    override suspend fun updateIsFirstLaunch(isFirstLaunch: Boolean): Boolean =
        preferencesDataStore
            .edit {
                it[KEY_IS_FIRST_LAUNCH] = isFirstLaunch
            }
            .let {
                it[KEY_IS_FIRST_LAUNCH]
                    ?: isFirstLaunchUpdateError(isFirstLaunch)
            }

    private fun isFirstLaunchUpdateError(isFirstLaunch: Boolean): Nothing =
        throw IllegalStateException(
            "Expecting $isFirstLaunch, but got null",
        )
}
