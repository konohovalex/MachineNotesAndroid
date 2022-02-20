package ru.konohovalex.feature.preferences.data.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(
    includes = [
        PreferencesDataCoroutinesModule::class,
        PreferencesDataMappersModule::class,
        PreferencesDataGsonModule::class,
        PreferencesDataStoreModule::class,
        PreferencesStorageModule::class,
        PreferencesRepositoryModule::class,
    ],
)
@InstallIn(SingletonComponent::class)
internal interface PreferencesDataModule
