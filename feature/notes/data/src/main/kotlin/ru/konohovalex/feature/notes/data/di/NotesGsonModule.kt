package ru.konohovalex.feature.notes.data.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.converter.gson.GsonConverterFactory
import ru.konohovalex.core.data.arch.provider.Provider
import ru.konohovalex.feature.notes.data.source.api.provider.NotesGsonProvider

@Module
@InstallIn(SingletonComponent::class)
internal class NotesGsonModule {
    @Provides
    fun provideNotesGsonProvider(): Provider<Nothing?, Gson> = NotesGsonProvider()

    @Provides
    fun provideNotesGson(
        notesGsonProvider: Provider<Nothing?, Gson>,
    ): Gson = notesGsonProvider.provide(null)

    @Provides
    fun provideNotesGsonConverterFactory(
        notesGson: Gson,
    ): GsonConverterFactory = GsonConverterFactory.create(notesGson)
}
