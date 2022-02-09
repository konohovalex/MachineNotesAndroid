package ru.konohovalex.feature.notes.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import kotlinx.coroutines.CoroutineScope
import ru.konohovalex.feature.notes.data.source.storage.contract.NotesStorageContract
import ru.konohovalex.feature.notes.data.source.storage.database.NotesDao
import ru.konohovalex.feature.notes.data.source.storage.impl.NotesStorageImpl
import javax.inject.Named
import javax.inject.Singleton

@Module
@DisableInstallInCheck
internal class NotesStorageModule {
    @Provides
    @Singleton
    fun provideNotesStorage(
        @Named(Qualifiers.NOTES_DATA_COROUTINES_SCOPE)
        notesDataCoroutineScope: CoroutineScope,
        notesDao: NotesDao,
    ): NotesStorageContract = NotesStorageImpl(notesDataCoroutineScope, notesDao)
}
