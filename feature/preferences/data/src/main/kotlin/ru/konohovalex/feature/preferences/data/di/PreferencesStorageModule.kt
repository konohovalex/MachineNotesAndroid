package ru.konohovalex.feature.preferences.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.konohovalex.core.data.arch.provider.Provider
import ru.konohovalex.feature.preferences.data.source.provider.PreferencesDataStoreProvider
import ru.konohovalex.feature.preferences.data.source.storage.contract.PreferencesStorageContract
import ru.konohovalex.feature.preferences.data.source.storage.impl.PreferencesStorage
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class PreferencesStorageModule {
    @Provides
    fun providePreferencesDataStoreProvider(): Provider<Context, DataStore<Preferences>> = PreferencesDataStoreProvider()

    @Provides
    @Singleton
    fun providePreferencesDataStore(
        @ApplicationContext context: Context,
        preferencesDataStoreProvider: Provider<Context, DataStore<Preferences>>,
    ): DataStore<Preferences> = preferencesDataStoreProvider.provide(context)

    @Provides
    @Singleton
    fun providePreferencesStorage(
        preferencesDataStore: DataStore<Preferences>,
    ): PreferencesStorageContract = PreferencesStorage(
        preferencesDataStore = preferencesDataStore,
    )
}
