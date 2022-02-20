package ru.konohovalex.feature.notes.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import ru.konohovalex.core.data.arch.provider.Provider
import ru.konohovalex.core.data.arch.source.network.api.provider.ApiProvider
import ru.konohovalex.feature.account.data.auth.repository.contract.AuthTokenRepositoryContract
import ru.konohovalex.feature.account.data.auth.source.network.interceptor.AuthInterceptor
import ru.konohovalex.feature.account.data.auth.source.network.interceptor.BearerAuthenticator
import ru.konohovalex.feature.account.data.auth.source.storage.contract.AuthDataStorageContract
import ru.konohovalex.feature.notes.data.source.network.api.NotesApi
import ru.konohovalex.feature.notes.data.source.network.api.provider.NotesApiProvider
import ru.konohovalex.feature.notes.data.source.network.api.provider.NotesApiProviderParams
import ru.konohovalex.feature.notes.data.source.network.api.provider.NotesOkHttpClientProvider
import ru.konohovalex.feature.notes.data.source.network.api.provider.NotesOkHttpClientProviderParams
import javax.inject.Named
import javax.inject.Singleton

@Module
@DisableInstallInCheck
internal class NotesNetworkModule {
    @Provides
    @Named(Qualifiers.NOTES_OK_HTTP_CLIENT_PROVIDER)
    fun provideNotesOkHttpClientProvider(): Provider<NotesOkHttpClientProviderParams, OkHttpClient> =
        NotesOkHttpClientProvider()

    @Provides
    @Singleton
    @Named(Qualifiers.NOTES_OK_HTTP_CLIENT)
    fun provideNotesOkHttpClient(
        @Named(Qualifiers.NOTES_OK_HTTP_CLIENT_PROVIDER)
        notesOkHttpClientProvider: Provider<NotesOkHttpClientProviderParams, OkHttpClient>,
        authTokenRepository: AuthTokenRepositoryContract,
        authDataStorage: AuthDataStorageContract,
    ): OkHttpClient = notesOkHttpClientProvider.provide(
        NotesOkHttpClientProviderParams(
            authenticator = BearerAuthenticator(authTokenRepository),
            interceptors = listOf(
                AuthInterceptor(authDataStorage),
            ),
        )
    )

    @Provides
    fun provideNotesApiProvider(): ApiProvider<NotesApiProviderParams, NotesApi> = NotesApiProvider()

    @Provides
    @Singleton
    fun provideNotesApi(
        notesApiProvider: ApiProvider<NotesApiProviderParams, NotesApi>,
        @Named(Qualifiers.NOTES_OK_HTTP_CLIENT)
        notesOkHttpClient: OkHttpClient,
        @Named(Qualifiers.NOTES_DATA_GSON_CONVERTER_FACTORY)
        notesGsonConverterFactory: GsonConverterFactory,
    ): NotesApi = notesApiProvider.provide(
        NotesApiProviderParams(
            okHttpClient = notesOkHttpClient,
            gsonConverterFactory = notesGsonConverterFactory,
        )
    )
}
