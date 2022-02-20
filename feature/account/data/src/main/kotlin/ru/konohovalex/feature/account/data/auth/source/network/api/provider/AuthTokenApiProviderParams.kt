package ru.konohovalex.feature.account.data.auth.source.network.api.provider

import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory

internal data class AuthTokenApiProviderParams(
    val okHttpClient: OkHttpClient,
    val gsonConverterFactory: GsonConverterFactory,
)
