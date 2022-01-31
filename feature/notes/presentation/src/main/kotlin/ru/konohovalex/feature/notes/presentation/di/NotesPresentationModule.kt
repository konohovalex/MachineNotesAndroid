package ru.konohovalex.feature.notes.presentation.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.konohovalex.feature.notes.presentation.details.di.NoteDetailsPresentationMappersModule
import ru.konohovalex.feature.notes.presentation.list.di.NoteListPresentationMappersModule

@Module(
    includes = [
        NoteDetailsPresentationMappersModule::class,
        NoteListPresentationMappersModule::class,
    ],
)
@InstallIn(SingletonComponent::class)
internal interface NotesPresentationModule
