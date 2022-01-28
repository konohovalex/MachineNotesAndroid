package ru.konohovalex.machinenotes.app.data.source.provider

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import ru.konohovalex.core.data.arch.provider.Provider
import ru.konohovalex.machinenotes.app.data.source.contract.AppPreferencesStorageContract
import javax.inject.Inject

internal class AppPreferencesDataStoreProvider
@Inject constructor() : Provider<Context, DataStore<Preferences>> {
    override fun provide(providerParams: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create {
            providerParams.preferencesDataStoreFile(name = AppPreferencesStorageContract.PREFERENCES_FILE_NAME)
        }
}
