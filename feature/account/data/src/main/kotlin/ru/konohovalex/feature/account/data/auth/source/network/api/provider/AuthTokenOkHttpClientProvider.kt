package ru.konohovalex.feature.account.data.auth.source.network.api.provider

import okhttp3.OkHttpClient
import ru.konohovalex.core.data.arch.provider.Provider
import javax.inject.Inject

internal class AuthTokenOkHttpClientProvider
@Inject constructor() : Provider<AuthTokenOkHttpClientProviderParams, OkHttpClient> {
    override fun provide(providerParams: AuthTokenOkHttpClientProviderParams): OkHttpClient =
        OkHttpClient.Builder()
            .apply {
                providerParams.interceptors.forEach(::addInterceptor)
            }
            .build()
}
