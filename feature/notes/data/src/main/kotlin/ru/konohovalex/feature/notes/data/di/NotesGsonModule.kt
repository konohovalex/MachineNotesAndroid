package ru.konohovalex.feature.notes.data.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import retrofit2.converter.gson.GsonConverterFactory
import ru.konohovalex.core.data.arch.provider.Provider
import ru.konohovalex.feature.notes.data.source.provider.NotesGsonProvider
import javax.inject.Named

@Module
@DisableInstallInCheck
internal class NotesGsonModule {
    @Provides
    @Named(Qualifiers.NOTES_GSON_PROVIDER)
    fun provideNotesGsonProvider(): Provider<Nothing?, Gson> = NotesGsonProvider()

    @Provides
    @Named(Qualifiers.NOTES_GSON)
    fun provideNotesGson(
        @Named(Qualifiers.NOTES_GSON_PROVIDER)
        notesGsonProvider: Provider<Nothing?, Gson>,
    ): Gson = notesGsonProvider.provide(null)

    @Provides
    @Named(Qualifiers.NOTES_GSON_CONVERTER_FACTORY)
    fun provideNotesGsonConverterFactory(
        @Named(Qualifiers.NOTES_GSON)
        notesGson: Gson,
    ): GsonConverterFactory = GsonConverterFactory.create(notesGson)
}
