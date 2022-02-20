package ru.konohovalex.feature.account.data.source.network.api.provider

import okhttp3.OkHttpClient
import ru.konohovalex.core.data.arch.provider.Provider
import javax.inject.Inject

internal class AccountOkHttpClientProvider
@Inject constructor() : Provider<AccountOkHttpClientProviderParams, OkHttpClient> {
    override fun provide(providerParams: AccountOkHttpClientProviderParams): OkHttpClient =
        OkHttpClient.Builder()
            .apply {
                authenticator(providerParams.authenticator)
                providerParams.interceptors.forEach(::addInterceptor)
            }
            .build()
}
