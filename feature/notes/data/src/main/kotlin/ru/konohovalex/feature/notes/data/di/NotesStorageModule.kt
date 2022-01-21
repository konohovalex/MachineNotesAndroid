package ru.konohovalex.feature.notes.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.konohovalex.core.data.arch.provider.Provider
import ru.konohovalex.feature.notes.data.source.storage.provider.NotesStorageProvider
import ru.konohovalex.feature.notes.data.source.storage.NotesDao
import ru.konohovalex.feature.notes.data.source.storage.NotesStorage
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class NotesStorageModule {
    @Provides
    fun provideNotesStorageProvider(): Provider<Context, NotesStorage> = NotesStorageProvider()

    @Provides
    @Singleton
    fun provideNotesStorage(
        @ApplicationContext context: Context,
        notesStorageProvider: Provider<Context, NotesStorage>,
    ): NotesStorage = notesStorageProvider.provide(context)

    @Provides
    @Singleton
    fun provideNotesDao(notesStorage: NotesStorage): NotesDao = notesStorage.getNotesDao()
}
