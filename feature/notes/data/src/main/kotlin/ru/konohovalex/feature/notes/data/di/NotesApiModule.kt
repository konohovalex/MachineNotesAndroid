package ru.konohovalex.feature.notes.data.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.converter.gson.GsonConverterFactory
import ru.konohovalex.core.data.model.TypeAdapterParams
import ru.konohovalex.core.data.arch.source.api.provider.ApiProvider
import ru.konohovalex.core.data.arch.provider.Provider
import ru.konohovalex.feature.notes.data.source.api.NotesApi
import ru.konohovalex.feature.notes.data.source.api.provider.NotesGsonConverterFactoryProvider
import ru.konohovalex.feature.notes.data.source.api.provider.NotesGsonProvider
import ru.konohovalex.feature.notes.data.source.api.provider.NotesGsonTypeAdapterProvider
import ru.konohovalex.feature.notes.data.source.api.provider.NotesApiProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class NotesApiModule {
    @Provides
    fun provideNotesGsonTypeAdapterProvider(): Provider<Nothing?, List<TypeAdapterParams>> = NotesGsonTypeAdapterProvider()

    @Provides
    fun provideNotesGsonProvider(): Provider<List<TypeAdapterParams>, Gson> = NotesGsonProvider()

    @Provides
    fun provideNotesGson(
        notesGsonTypeAdapterProvider: Provider<Nothing?, List<TypeAdapterParams>>,
        notesGsonProvider: Provider<List<TypeAdapterParams>, Gson>,
    ): Gson = notesGsonProvider.provide(notesGsonTypeAdapterProvider.provide(null))

    @Provides
    fun provideNotesGsonConverterFactory(
        notesGson: Gson,
    ): GsonConverterFactory = NotesGsonConverterFactoryProvider().provide(notesGson)

    @Provides
    fun provideNotesApiProvider(): ApiProvider<GsonConverterFactory, NotesApi> = NotesApiProvider()

    @Provides
    @Singleton
    fun provideNotesApi(
        notesApiProvider: ApiProvider<GsonConverterFactory, NotesApi>,
        notesGsonConverterFactory: GsonConverterFactory,
    ): NotesApi = notesApiProvider.provide(notesGsonConverterFactory)
}
