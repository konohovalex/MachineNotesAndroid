package ru.konohovalex.feature.account.data.auth.di

import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import ru.konohovalex.core.data.arch.provider.Provider
import ru.konohovalex.core.data.arch.source.network.api.provider.ApiProvider
import ru.konohovalex.feature.account.data.auth.source.network.api.AuthTokenApi
import ru.konohovalex.feature.account.data.auth.source.network.api.provider.AuthTokenApiProvider
import ru.konohovalex.feature.account.data.auth.source.network.api.provider.AuthTokenApiProviderParams
import ru.konohovalex.feature.account.data.auth.source.network.api.provider.AuthTokenOkHttpClientProvider
import ru.konohovalex.feature.account.data.auth.source.network.api.provider.AuthTokenOkHttpClientProviderParams
import ru.konohovalex.feature.account.data.auth.source.network.interceptor.AuthInterceptor
import ru.konohovalex.feature.account.data.auth.source.storage.contract.AuthDataStorageContract
import ru.konohovalex.feature.account.data.di.Qualifiers
import javax.inject.Named
import javax.inject.Singleton

@Module
@DisableInstallInCheck
internal class AuthTokenNetworkModule {
    @Provides
    @Named(Qualifiers.AUTH_TOKEN_OK_HTTP_CLIENT_PROVIDER)
    fun provideAuthTokenOkHttpClientProvider(): Provider<AuthTokenOkHttpClientProviderParams, OkHttpClient> =
        AuthTokenOkHttpClientProvider()

    @Provides
    @Singleton
    @Named(Qualifiers.AUTH_TOKEN_OK_HTTP_CLIENT)
    fun provideAuthTokenOkHttpClient(
        @Named(Qualifiers.AUTH_TOKEN_OK_HTTP_CLIENT_PROVIDER)
        authTokenOkHttpClientProvider: Provider<AuthTokenOkHttpClientProviderParams, OkHttpClient>,
        authDataStorageContract: AuthDataStorageContract,
    ): OkHttpClient = authTokenOkHttpClientProvider.provide(
        AuthTokenOkHttpClientProviderParams(
            interceptors = listOf(
                AuthInterceptor(authDataStorageContract),
            )
        )
    )

    @Provides
    fun provideAuthTokenApiProvider(): ApiProvider<AuthTokenApiProviderParams, AuthTokenApi> = AuthTokenApiProvider()

    @Provides
    @Singleton
    fun provideAuthTokenApi(
        authTokenApiProvider: ApiProvider<AuthTokenApiProviderParams, AuthTokenApi>,
        @Named(Qualifiers.AUTH_TOKEN_OK_HTTP_CLIENT)
        authTokenOkHttpClient: OkHttpClient,
        @Named(Qualifiers.ACCOUNT_DATA_GSON_CONVERTER_FACTORY)
        accountGsonConverterFactory: GsonConverterFactory,
    ): AuthTokenApi = authTokenApiProvider.provide(
        AuthTokenApiProviderParams(
            okHttpClient = authTokenOkHttpClient,
            gsonConverterFactory = accountGsonConverterFactory,
        )
    )
}
