package ru.konohovalex.feature.account.data.source.network.api.provider

import okhttp3.Authenticator
import okhttp3.Interceptor

internal data class AccountOkHttpClientProviderParams(
    val authenticator: Authenticator,
    val interceptors: List<Interceptor>,
)
