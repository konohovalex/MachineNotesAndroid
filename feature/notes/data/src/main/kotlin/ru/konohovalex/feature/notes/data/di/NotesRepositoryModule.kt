package ru.konohovalex.feature.notes.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.konohovalex.feature.notes.data.repository.contract.NotesRepositoryContract
import ru.konohovalex.feature.notes.data.repository.impl.NotesRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface NotesRepositoryModule {
    @Binds
    @Singleton
    fun bindNotesRepository(notesRepository: NotesRepositoryImpl): NotesRepositoryContract
}
