package ru.konohovalex.feature.notes.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.migration.DisableInstallInCheck
import ru.konohovalex.core.data.arch.provider.Provider
import ru.konohovalex.feature.notes.data.source.storage.database.NotesDao
import ru.konohovalex.feature.notes.data.source.storage.database.NotesDatabase
import ru.konohovalex.feature.notes.data.source.storage.database.provider.NotesDatabaseProvider
import javax.inject.Singleton

@Module
@DisableInstallInCheck
internal class NotesDatabaseModule {
    @Provides
    fun provideNotesDatabaseProvider(): Provider<Context, NotesDatabase> =
        NotesDatabaseProvider()

    @Provides
    @Singleton
    fun provideNotesDatabase(
        @ApplicationContext
        context: Context,
        notesDatabaseProvider: Provider<Context, NotesDatabase>,
    ): NotesDatabase = notesDatabaseProvider.provide(context)

    @Provides
    @Singleton
    fun provideNotesDao(notesDatabase: NotesDatabase): NotesDao = notesDatabase.getNotesDao()
}
