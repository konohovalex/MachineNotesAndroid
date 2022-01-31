package ru.konohovalex.feature.account.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import retrofit2.converter.gson.GsonConverterFactory
import ru.konohovalex.core.data.arch.source.api.provider.ApiProvider
import ru.konohovalex.feature.account.data.source.api.AccountApi
import ru.konohovalex.feature.account.data.source.api.provider.AccountApiProvider
import javax.inject.Named
import javax.inject.Singleton

@Module
@DisableInstallInCheck
internal class AccountApiModule {
    @Provides
    fun provideAccountApiProvider(): ApiProvider<GsonConverterFactory, AccountApi> = AccountApiProvider()

    @Provides
    @Singleton
    fun provideAccountApi(
        accountApiProvider: ApiProvider<GsonConverterFactory, AccountApi>,
        @Named(Qualifiers.ACCOUNT_GSON_CONVERTER_FACTORY)
        accountGsonConverterFactory: GsonConverterFactory,
    ): AccountApi = accountApiProvider.provide(accountGsonConverterFactory)
}
