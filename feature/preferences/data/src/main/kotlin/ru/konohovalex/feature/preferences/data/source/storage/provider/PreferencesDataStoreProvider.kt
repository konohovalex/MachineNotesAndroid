package ru.konohovalex.feature.preferences.data.source.storage.provider

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import ru.konohovalex.core.data.arch.provider.Provider
import ru.konohovalex.feature.preferences.data.source.storage.contract.PreferencesStorageContract
import javax.inject.Inject

internal class PreferencesDataStoreProvider
@Inject constructor() : Provider<Context, DataStore<Preferences>> {
    override fun provide(providerParams: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create {
            providerParams.preferencesDataStoreFile(name = PreferencesStorageContract.PREFERENCES_FILE_NAME)
        }
}
