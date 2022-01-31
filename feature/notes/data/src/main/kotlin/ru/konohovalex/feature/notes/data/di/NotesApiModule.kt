package ru.konohovalex.feature.notes.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import retrofit2.converter.gson.GsonConverterFactory
import ru.konohovalex.core.data.arch.source.api.provider.ApiProvider
import ru.konohovalex.feature.notes.data.source.api.NotesApi
import ru.konohovalex.feature.notes.data.source.api.provider.NotesApiProvider
import javax.inject.Named
import javax.inject.Singleton

@Module
@DisableInstallInCheck
internal class NotesApiModule {
    @Provides
    fun provideNotesApiProvider(): ApiProvider<GsonConverterFactory, NotesApi> = NotesApiProvider()

    @Provides
    @Singleton
    fun provideNotesApi(
        notesApiProvider: ApiProvider<GsonConverterFactory, NotesApi>,
        @Named(Qualifiers.NOTES_GSON_CONVERTER_FACTORY)
        notesGsonConverterFactory: GsonConverterFactory,
    ): NotesApi = notesApiProvider.provide(notesGsonConverterFactory)
}
