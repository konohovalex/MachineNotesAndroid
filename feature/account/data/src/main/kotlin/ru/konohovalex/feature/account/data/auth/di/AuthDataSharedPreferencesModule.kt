package ru.konohovalex.feature.account.data.auth.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.migration.DisableInstallInCheck
import ru.konohovalex.core.data.arch.provider.Provider
import ru.konohovalex.feature.account.data.auth.source.storage.provider.AuthDataSharedPreferencesProvider
import ru.konohovalex.feature.account.data.di.Qualifiers
import javax.inject.Named
import javax.inject.Singleton

@Module
@DisableInstallInCheck
class AuthDataSharedPreferencesModule {
    @Provides
    @Named(Qualifiers.AUTH_DATA_SHARED_PREFERENCES_PROVIDER)
    fun provideAuthDataSharedPreferencesProvider(): Provider<Context, SharedPreferences> =
        AuthDataSharedPreferencesProvider()

    @Provides
    @Singleton
    @Named(Qualifiers.AUTH_DATA_SHARED_PREFERENCES)
    fun provideAuthDataSharedPreferences(
        @ApplicationContext
        context: Context,
        @Named(Qualifiers.AUTH_DATA_SHARED_PREFERENCES_PROVIDER)
        authDataSharedPreferencesProvider: Provider<Context, SharedPreferences>,
    ): SharedPreferences = authDataSharedPreferencesProvider.provide(context)
}
