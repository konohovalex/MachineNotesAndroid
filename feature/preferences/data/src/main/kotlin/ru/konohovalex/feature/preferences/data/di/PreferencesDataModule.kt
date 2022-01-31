package ru.konohovalex.feature.preferences.data.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(
    includes = [
        PreferencesDataMappersModule::class,
        PreferencesGsonModule::class,
        PreferencesStorageModule::class,
        PreferencesRepositoryModule::class,
    ],
)
@InstallIn(SingletonComponent::class)
internal interface PreferencesDataModule
