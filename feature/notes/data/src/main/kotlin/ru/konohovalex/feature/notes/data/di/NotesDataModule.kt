package ru.konohovalex.feature.notes.data.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(
    includes = [
        NotesDataCoroutinesModule::class,
        NotesDataMappersModule::class,
        NotesDataGsonModule::class,
        NotesNetworkModule::class,
        NotesDatabaseModule::class,
        NotesStorageModule::class,
        NotesRepositoryModule::class,
    ],
)
@InstallIn(SingletonComponent::class)
internal interface NotesDataModule
