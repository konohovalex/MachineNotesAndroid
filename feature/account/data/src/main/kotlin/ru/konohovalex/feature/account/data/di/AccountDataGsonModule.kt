package ru.konohovalex.feature.account.data.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import retrofit2.converter.gson.GsonConverterFactory
import ru.konohovalex.core.data.arch.provider.Provider
import ru.konohovalex.feature.account.data.source.provider.AccountDataGsonProvider
import javax.inject.Named

@Module
@DisableInstallInCheck
internal class AccountDataGsonModule {
    @Provides
    @Named(Qualifiers.ACCOUNT_DATA_GSON_PROVIDER)
    fun provideAccountGsonProvider(): Provider<Nothing?, Gson> = AccountDataGsonProvider()

    @Provides
    @Named(Qualifiers.ACCOUNT_DATA_GSON)
    fun provideAccountGson(
        @Named(Qualifiers.ACCOUNT_DATA_GSON_PROVIDER)
        accountGsonProvider: Provider<Nothing?, Gson>,
    ): Gson = accountGsonProvider.provide(null)

    @Provides
    @Named(Qualifiers.ACCOUNT_DATA_GSON_CONVERTER_FACTORY)
    fun provideAccountGsonConverterFactory(
        @Named(Qualifiers.ACCOUNT_DATA_GSON)
        accountGson: Gson,
    ): GsonConverterFactory = GsonConverterFactory.create(accountGson)
}
