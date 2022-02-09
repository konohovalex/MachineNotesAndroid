package ru.konohovalex.feature.notes.data.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(
    includes = [
        NotesDataCoroutinesModule::class,
        NotesApiModule::class,
        NotesDataMappersModule::class,
        NotesGsonModule::class,
        NotesRepositoryModule::class,
        NotesDatabaseModule::class,
        NotesStorageModule::class,
    ],
)
@InstallIn(SingletonComponent::class)
internal interface NotesDataModule
