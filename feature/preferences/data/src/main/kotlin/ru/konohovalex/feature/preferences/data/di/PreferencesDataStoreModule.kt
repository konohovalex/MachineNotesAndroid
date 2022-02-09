package ru.konohovalex.feature.preferences.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.migration.DisableInstallInCheck
import ru.konohovalex.core.data.arch.provider.Provider
import ru.konohovalex.feature.preferences.data.source.storage.provider.PreferencesDataStoreProvider
import javax.inject.Named
import javax.inject.Singleton

@Module
@DisableInstallInCheck
internal class PreferencesDataStoreModule {
    @Provides
    @Named(Qualifiers.PREFERENCES_DATA_STORE_PROVIDER)
    fun providePreferencesDataStoreProvider(): Provider<Context, DataStore<Preferences>> =
        PreferencesDataStoreProvider()

    @Provides
    @Singleton
    @Named(Qualifiers.PREFERENCES_DATA_STORE)
    fun providePreferencesDataStore(
        @ApplicationContext
        context: Context,
        @Named(Qualifiers.PREFERENCES_DATA_STORE_PROVIDER)
        preferencesDataStoreProvider: Provider<Context, DataStore<Preferences>>,
    ): DataStore<Preferences> = preferencesDataStoreProvider.provide(context)
}
