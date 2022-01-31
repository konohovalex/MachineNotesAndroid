package ru.konohovalex.feature.preferences.presentation.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(
    includes = [
        PreferencesPresentationMappersModule::class,
    ],
)
@InstallIn(SingletonComponent::class)
internal interface PreferencesPresentationModule
