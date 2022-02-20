package ru.konohovalex.feature.account.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import ru.konohovalex.core.data.arch.provider.Provider
import ru.konohovalex.core.data.arch.source.network.api.provider.ApiProvider
import ru.konohovalex.feature.account.data.auth.repository.contract.AuthTokenRepositoryContract
import ru.konohovalex.feature.account.data.auth.source.network.interceptor.AuthInterceptor
import ru.konohovalex.feature.account.data.auth.source.network.interceptor.BearerAuthenticator
import ru.konohovalex.feature.account.data.auth.source.storage.contract.AuthDataStorageContract
import ru.konohovalex.feature.account.data.source.network.api.AccountApi
import ru.konohovalex.feature.account.data.source.network.api.provider.AccountApiProvider
import ru.konohovalex.feature.account.data.source.network.api.provider.AccountApiProviderParams
import ru.konohovalex.feature.account.data.source.network.api.provider.AccountOkHttpClientProvider
import ru.konohovalex.feature.account.data.source.network.api.provider.AccountOkHttpClientProviderParams
import javax.inject.Named
import javax.inject.Singleton

@Module
@DisableInstallInCheck
internal class AccountNetworkModule {
    @Provides
    @Named(Qualifiers.ACCOUNT_OK_HTTP_CLIENT_PROVIDER)
    fun provideAccountOkHttpClientProvider(): Provider<AccountOkHttpClientProviderParams, OkHttpClient> =
        AccountOkHttpClientProvider()

    @Provides
    @Singleton
    @Named(Qualifiers.ACCOUNT_OK_HTTP_CLIENT)
    fun provideAccountOkHttpClient(
        @Named(Qualifiers.ACCOUNT_OK_HTTP_CLIENT_PROVIDER)
        accountOkHttpClientProvider: Provider<AccountOkHttpClientProviderParams, OkHttpClient>,
        authTokenRepository: AuthTokenRepositoryContract,
        authDataStorage: AuthDataStorageContract,
    ): OkHttpClient = accountOkHttpClientProvider.provide(
        AccountOkHttpClientProviderParams(
            authenticator = BearerAuthenticator(authTokenRepository),
            interceptors = listOf(
                AuthInterceptor(authDataStorage),
            ),
        )
    )

    @Provides
    fun provideAccountApiProvider(): ApiProvider<AccountApiProviderParams, AccountApi> = AccountApiProvider()

    @Provides
    @Singleton
    fun provideAccountApi(
        accountApiProvider: ApiProvider<AccountApiProviderParams, AccountApi>,
        @Named(Qualifiers.ACCOUNT_OK_HTTP_CLIENT)
        accountOkHttpClient: OkHttpClient,
        @Named(Qualifiers.ACCOUNT_DATA_GSON_CONVERTER_FACTORY)
        accountGsonConverterFactory: GsonConverterFactory,
    ): AccountApi = accountApiProvider.provide(
        AccountApiProviderParams(
            okHttpClient = accountOkHttpClient,
            gsonConverterFactory = accountGsonConverterFactory,
        )
    )
}
