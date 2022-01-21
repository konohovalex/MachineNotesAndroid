package ru.konohovalex.feature.preferences.data.source.provider

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import ru.konohovalex.core.data.arch.provider.Provider
import ru.konohovalex.feature.preferences.data.source.storage.impl.PreferencesStorage
import javax.inject.Inject

internal class PreferencesDataStoreProvider
@Inject constructor() : Provider<Context, DataStore<Preferences>> {
    override fun provide(providerParams: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create {
            providerParams.preferencesDataStoreFile(name = PreferencesStorage.PREFERENCES_FILE_NAME)
        }
}
