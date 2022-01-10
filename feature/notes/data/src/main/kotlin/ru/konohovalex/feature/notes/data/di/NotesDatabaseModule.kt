package ru.konohovalex.feature.notes.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.konohovalex.core.data.source.contract.provider.Provider
import ru.konohovalex.feature.notes.data.source.provider.NotesDatabaseProvider
import ru.konohovalex.feature.notes.data.source.storage.NotesDao
import ru.konohovalex.feature.notes.data.source.storage.NotesDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class NotesDatabaseModule {
    @Provides
    fun provideNotesDatabaseProvider(): Provider<Context, NotesDatabase> = NotesDatabaseProvider()

    @Provides
    @Singleton
    fun provideNotesDatabase(
        @ApplicationContext context: Context,
        notesDatabaseProvider: Provider<Context, NotesDatabase>,
    ): NotesDatabase = notesDatabaseProvider.provide(context)

    @Provides
    @Singleton
    fun provideNotesDao(notesDatabase: NotesDatabase): NotesDao = notesDatabase.getNotesDao()
}
