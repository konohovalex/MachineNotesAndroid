package ru.konohovalex.feature.notes.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck
import ru.konohovalex.feature.notes.data.repository.contract.NotesRepositoryContract
import ru.konohovalex.feature.notes.data.repository.impl.NotesRepositoryImpl
import javax.inject.Singleton

@Module
@DisableInstallInCheck
internal interface NotesRepositoryModule {
    @Binds
    @Singleton
    fun bindNotesRepository(notesRepository: NotesRepositoryImpl): NotesRepositoryContract
}
