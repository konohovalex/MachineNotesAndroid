package ru.konohovalex.feature.notes.data.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import retrofit2.converter.gson.GsonConverterFactory
import ru.konohovalex.core.data.arch.provider.Provider
import ru.konohovalex.feature.notes.data.source.provider.NotesDataGsonProvider
import javax.inject.Named

@Module
@DisableInstallInCheck
internal class NotesDataGsonModule {
    @Provides
    @Named(Qualifiers.NOTES_DATA_GSON_PROVIDER)
    fun provideNotesDataGsonProvider(): Provider<Nothing?, Gson> = NotesDataGsonProvider()

    @Provides
    @Named(Qualifiers.NOTES_DATA_GSON)
    fun provideNotesDataGson(
        @Named(Qualifiers.NOTES_DATA_GSON_PROVIDER)
        notesGsonProvider: Provider<Nothing?, Gson>,
    ): Gson = notesGsonProvider.provide(null)

    @Provides
    @Named(Qualifiers.NOTES_DATA_GSON_CONVERTER_FACTORY)
    fun provideNotesDataGsonConverterFactory(
        @Named(Qualifiers.NOTES_DATA_GSON)
        notesGson: Gson,
    ): GsonConverterFactory = GsonConverterFactory.create(notesGson)
}
