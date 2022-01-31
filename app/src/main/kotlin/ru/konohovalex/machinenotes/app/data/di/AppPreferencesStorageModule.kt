package ru.konohovalex.machinenotes.app.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.migration.DisableInstallInCheck
import ru.konohovalex.core.data.arch.provider.Provider
import ru.konohovalex.machinenotes.app.data.source.contract.AppPreferencesStorageContract
import ru.konohovalex.machinenotes.app.data.source.impl.AppPreferencesStorageImpl
import ru.konohovalex.machinenotes.app.data.source.provider.AppPreferencesDataStoreProvider
import javax.inject.Named
import javax.inject.Singleton

@Module
@DisableInstallInCheck
internal class AppPreferencesStorageModule {
    @Provides
    @Named(Qualifiers.APP_PREFERENCES_DATA_STORE_PROVIDER)
    fun provideAppPreferencesDataStoreProvider(): Provider<Context, DataStore<Preferences>> =
        AppPreferencesDataStoreProvider()

    @Provides
    @Singleton
    @Named(Qualifiers.APP_PREFERENCES_DATA_STORE)
    fun provideAppPreferencesDataStore(
        @ApplicationContext
        context: Context,
        @Named(Qualifiers.APP_PREFERENCES_DATA_STORE_PROVIDER)
        appPreferencesDataStoreProvider: Provider<Context, DataStore<Preferences>>,
    ): DataStore<Preferences> = appPreferencesDataStoreProvider.provide(context)

    @Provides
    @Singleton
    fun provideProfileStorage(
        @Named(Qualifiers.APP_PREFERENCES_DATA_STORE)
        appPreferencesDataStore: DataStore<Preferences>,
    ): AppPreferencesStorageContract = AppPreferencesStorageImpl(
        preferencesDataStore = appPreferencesDataStore,
    )
}
