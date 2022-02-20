package ru.konohovalex.feature.notes.data.source.network.api.provider

import okhttp3.OkHttpClient
import ru.konohovalex.core.data.arch.provider.Provider
import javax.inject.Inject

internal class NotesOkHttpClientProvider
@Inject constructor() : Provider<NotesOkHttpClientProviderParams, OkHttpClient> {
    override fun provide(providerParams: NotesOkHttpClientProviderParams): OkHttpClient =
        OkHttpClient.Builder()
            .apply {
                authenticator(providerParams.authenticator)
                providerParams.interceptors.forEach(::addInterceptor)
            }
            .build()
}
