package ru.konohovalex.feature.notes.data.source.network.api.provider

import okhttp3.Authenticator
import okhttp3.Interceptor

internal data class NotesOkHttpClientProviderParams(
    val authenticator: Authenticator,
    val interceptors: List<Interceptor>,
)
