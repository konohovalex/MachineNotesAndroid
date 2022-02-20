package ru.konohovalex.feature.account.data.auth.di

import android.content.SharedPreferences
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import ru.konohovalex.feature.account.data.auth.source.storage.contract.AuthDataStorageContract
import ru.konohovalex.feature.account.data.auth.source.storage.impl.AuthDataStorageImpl
import ru.konohovalex.feature.account.data.di.Qualifiers
import javax.inject.Named
import javax.inject.Singleton

@Module
@DisableInstallInCheck
internal class AuthDataStorageModule {
    @Provides
    @Singleton
    fun provideAuthDataStorage(
        @Named(Qualifiers.AUTH_DATA_SHARED_PREFERENCES)
        authDataSharedPreferences: SharedPreferences,
        @Named(Qualifiers.ACCOUNT_DATA_GSON)
        accountDataGson: Gson,
    ): AuthDataStorageContract = AuthDataStorageImpl(authDataSharedPreferences, accountDataGson)
}
