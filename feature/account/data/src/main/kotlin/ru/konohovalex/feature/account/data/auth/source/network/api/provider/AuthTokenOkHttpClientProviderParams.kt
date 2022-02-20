package ru.konohovalex.feature.account.data.auth.source.network.api.provider

import okhttp3.Interceptor

internal data class AuthTokenOkHttpClientProviderParams(val interceptors: List<Interceptor>)
