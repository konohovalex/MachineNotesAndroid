package ru.konohovalex.feature.account.data.profile.di

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
import ru.konohovalex.feature.account.data.di.Qualifiers
import ru.konohovalex.feature.account.data.profile.source.provider.ProfileDataStoreCoroutineScopeProvider
import ru.konohovalex.feature.account.data.profile.source.storage.contract.ProfileStorageContract
import ru.konohovalex.feature.account.data.profile.source.storage.impl.ProfileStorageImpl
import ru.konohovalex.feature.account.data.profile.source.storage.provider.ProfileDataStoreProvider
import javax.inject.Named
import javax.inject.Singleton

@Module
@DisableInstallInCheck
internal class ProfileStorageModule {
    @Provides
    @Named(Qualifiers.PROFILE_DATA_STORE_COROUTINE_SCOPE_PROVIDER)
    fun provideProfileDataStoreCoroutineScopeProvider(): Provider<Nothing?, CoroutineScope> =
        ProfileDataStoreCoroutineScopeProvider()

    @Provides
    @Named(Qualifiers.PROFILE_DATA_STORE_COROUTINE_SCOPE)
    fun provideProfileDataStoreCoroutineScope(
        @Named(Qualifiers.PROFILE_DATA_STORE_COROUTINE_SCOPE_PROVIDER)
        profileDataStoreCoroutineScopeProvider: Provider<Nothing?, CoroutineScope>,
    ): CoroutineScope = profileDataStoreCoroutineScopeProvider.provide(null)

    @Provides
    @Named(Qualifiers.PROFILE_DATA_STORE_PROVIDER)
    fun provideProfileDataStoreProvider(): Provider<Context, DataStore<Preferences>> =
        ProfileDataStoreProvider()

    @Provides
    @Singleton
    @Named(Qualifiers.PROFILE_PREFERENCES_DATA_STORE)
    fun provideProfilePreferencesDataStore(
        @ApplicationContext
        context: Context,
        @Named(Qualifiers.PROFILE_DATA_STORE_PROVIDER)
        profilePreferencesDataStoreProvider: Provider<Context, DataStore<Preferences>>,
    ): DataStore<Preferences> = profilePreferencesDataStoreProvider.provide(context)

    @Provides
    @Singleton
    fun provideProfileStorage(
        @Named(Qualifiers.PROFILE_DATA_STORE_COROUTINE_SCOPE)
        profileDataStoreCoroutineScope: CoroutineScope,
        @Named(Qualifiers.PROFILE_PREFERENCES_DATA_STORE)
        profilePreferencesDataStore: DataStore<Preferences>,
        @Named(Qualifiers.PROFILE_GSON)
        profileGson: Gson,
    ): ProfileStorageContract = ProfileStorageImpl(
        coroutineScope = profileDataStoreCoroutineScope,
        preferencesDataStore = profilePreferencesDataStore,
        gson = profileGson,
    )
}
