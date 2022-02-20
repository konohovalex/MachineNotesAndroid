package ru.konohovalex.feature.account.data.profile.source.storage.provider

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import ru.konohovalex.core.data.arch.provider.Provider
import ru.konohovalex.feature.account.data.profile.source.storage.contract.ProfileStorageContract
import javax.inject.Inject

internal class ProfileDataStoreProvider
@Inject constructor() : Provider<Context, DataStore<Preferences>> {
    override fun provide(providerParams: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create {
            providerParams.preferencesDataStoreFile(name = ProfileStorageContract.PREFERENCES_FILE_NAME)
        }
}
