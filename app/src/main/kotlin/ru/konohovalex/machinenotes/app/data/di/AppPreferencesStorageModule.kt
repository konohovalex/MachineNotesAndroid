package ru.konohovalex.machinenotes.app.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.konohovalex.machinenotes.app.data.source.contract.AppPreferencesStorageContract
import ru.konohovalex.machinenotes.app.data.source.impl.AppPreferencesStorageImpl
import ru.konohovalex.machinenotes.app.data.source.provider.AppPreferencesDataStoreProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class AppPreferencesStorageModule {
    // tbd fix
    /*@Provides
    fun provideAppPreferencesDataStoreProvider(): Provider<Context, DataStore<Preferences>> = AppPreferencesDataStoreProvider()*/

    /*@Provides
    @Singleton
    fun provideAppPreferencesDataStore(
        @ApplicationContext context: Context,
        appPreferencesDataStoreProvider: Provider<Context, DataStore<Preferences>>,
    ): DataStore<Preferences> = appPreferencesDataStoreProvider.provide(context)*/

    @Provides
    @Singleton
    fun provideProfileStorage(
        @ApplicationContext context: Context,
//        appPreferencesDataStore: DataStore<Preferences>,
    ): AppPreferencesStorageContract = AppPreferencesStorageImpl(
        preferencesDataStore = AppPreferencesDataStoreProvider().provide(context),
    )
}
