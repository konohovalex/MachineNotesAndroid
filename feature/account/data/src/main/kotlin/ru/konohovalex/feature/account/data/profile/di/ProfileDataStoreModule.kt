package ru.konohovalex.feature.account.data.profile.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.migration.DisableInstallInCheck
import ru.konohovalex.core.data.arch.provider.Provider
import ru.konohovalex.feature.account.data.di.Qualifiers
import ru.konohovalex.feature.account.data.profile.source.storage.provider.ProfileDataStoreProvider
import javax.inject.Named
import javax.inject.Singleton

@Module
@DisableInstallInCheck
internal class ProfileDataStoreModule {
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
}
