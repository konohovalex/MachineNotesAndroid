package ru.konohovalex.feature.notes.domain.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(
    includes = [
        NotesDomainMappersModule::class,
    ],
)
@InstallIn(SingletonComponent::class)
internal interface NotesDomainModule
