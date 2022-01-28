package ru.konohovalex.feature.account.data.profile.di

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.konohovalex.feature.account.data.profile.source.storage.contract.ProfileStorageContract
import ru.konohovalex.feature.account.data.profile.source.storage.impl.ProfileStorageImpl
import ru.konohovalex.feature.account.data.profile.source.storage.provider.ProfileDataStoreProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ProfileStorageModule {
    // tbd fix
    /*@Provides
    fun provideProfileDataStoreProvider(): Provider<Context, DataStore<Preferences>> = ProfileDataStoreProvider()*/

    /*@Provides
    @Singleton
    fun provideProfilePreferencesDataStore(
        @ApplicationContext context: Context,
        profilePreferencesDataStoreProvider: Provider<Context, DataStore<Preferences>>,
    ): DataStore<Preferences> = profilePreferencesDataStoreProvider.provide(context)*/

    @Provides
    @Singleton
    fun provideProfileStorage(
        @ApplicationContext context: Context,
//        profilePreferencesDataStore: DataStore<Preferences>,
        profileGson: Gson,
    ): ProfileStorageContract = ProfileStorageImpl(
        preferencesDataStore = ProfileDataStoreProvider().provide(context),
        gson = profileGson,
    )
}
