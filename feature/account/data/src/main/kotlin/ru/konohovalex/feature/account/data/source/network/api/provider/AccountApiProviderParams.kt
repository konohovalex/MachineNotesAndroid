package ru.konohovalex.feature.account.data.source.network.api.provider

import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory

internal data class AccountApiProviderParams(
    val okHttpClient: OkHttpClient,
    val gsonConverterFactory: GsonConverterFactory,
)
