package ru.konohovalex.feature.notes.data.source.network.api.provider

import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory

internal data class NotesApiProviderParams(
    val okHttpClient: OkHttpClient,
    val gsonConverterFactory: GsonConverterFactory,
)
