package ru.konohovalex.feature.preferences.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.migration.DisableInstallInCheck
import kotlinx.coroutines.CoroutineScope
import ru.konohovalex.core.data.arch.provider.Provider
import ru.konohovalex.feature.preferences.data.source.provider.PreferencesDataStoreCoroutineScopeProvider
import ru.konohovalex.feature.preferences.data.source.storage.contract.PreferencesStorageContract
import ru.konohovalex.feature.preferences.data.source.storage.impl.PreferencesStorageImpl
import ru.konohovalex.feature.preferences.data.source.storage.provider.PreferencesDataStoreProvider
import javax.inject.Named
import javax.inject.Singleton

@Module
@DisableInstallInCheck
internal class PreferencesStorageModule {
    @Provides
    @Named(Qualifiers.PREFERENCES_DATA_STORE_COROUTINE_SCOPE_PROVIDER)
    fun providePreferencesDataStoreCoroutineScopeProvider(): Provider<Nothing?, CoroutineScope> =
        PreferencesDataStoreCoroutineScopeProvider()

    @Provides
    @Named(Qualifiers.PREFERENCES_DATA_STORE_COROUTINE_SCOPE)
    fun providePreferencesDataStoreCoroutineScope(
        @Named(Qualifiers.PREFERENCES_DATA_STORE_COROUTINE_SCOPE_PROVIDER)
        preferencesDataStoreCoroutineScopeProvider: Provider<Nothing?, CoroutineScope>,
    ): CoroutineScope = preferencesDataStoreCoroutineScopeProvider.provide(null)

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

    @Provides
    @Singleton
    fun providePreferencesStorage(
        @Named(Qualifiers.PREFERENCES_DATA_STORE_COROUTINE_SCOPE)
        preferencesDataStoreCoroutinesScope: CoroutineScope,
        @Named(Qualifiers.PREFERENCES_DATA_STORE)
        preferencesDataStore: DataStore<Preferences>,
        @Named(Qualifiers.PREFERENCES_GSON)
        preferencesGson: Gson,
    ): PreferencesStorageContract = PreferencesStorageImpl(
        coroutineScope = preferencesDataStoreCoroutinesScope,
        preferencesDataStore = preferencesDataStore,
        gson = preferencesGson,
    )
}
