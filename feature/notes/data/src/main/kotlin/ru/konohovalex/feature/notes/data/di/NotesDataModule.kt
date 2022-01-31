package ru.konohovalex.feature.notes.data.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(
    includes = [
        NotesApiModule::class,
        NotesDataMappersModule::class,
        NotesGsonModule::class,
        NotesRepositoryModule::class,
        NotesStorageModule::class,
    ],
)
@InstallIn(SingletonComponent::class)
internal interface NotesDataModule
