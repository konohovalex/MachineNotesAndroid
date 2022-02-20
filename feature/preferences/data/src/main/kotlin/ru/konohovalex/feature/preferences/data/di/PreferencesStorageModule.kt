package ru.konohovalex.feature.preferences.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import kotlinx.coroutines.CoroutineScope
import ru.konohovalex.feature.preferences.data.source.storage.contract.PreferencesStorageContract
import ru.konohovalex.feature.preferences.data.source.storage.impl.PreferencesStorageImpl
import javax.inject.Named
import javax.inject.Singleton

@Module
@DisableInstallInCheck
internal class PreferencesStorageModule {
    @Provides
    @Singleton
    fun providePreferencesStorage(
        @Named(Qualifiers.PREFERENCES_DATA_COROUTINE_SCOPE)
        preferencesDataCoroutinesScope: CoroutineScope,
        @Named(Qualifiers.PREFERENCES_DATA_STORE)
        preferencesDataStore: DataStore<Preferences>,
        @Named(Qualifiers.PREFERENCES_DATA_GSON)
        preferencesGson: Gson,
    ): PreferencesStorageContract = PreferencesStorageImpl(
        coroutineScope = preferencesDataCoroutinesScope,
        preferencesDataStore = preferencesDataStore,
        gson = preferencesGson,
    )
}
