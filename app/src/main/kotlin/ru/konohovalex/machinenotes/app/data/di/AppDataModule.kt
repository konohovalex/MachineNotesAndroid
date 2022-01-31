package ru.konohovalex.machinenotes.app.data.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(
    includes = [
        AppPreferencesStorageModule::class,
        AppPreferencesRepositoryModule::class,
    ],
)
@InstallIn(SingletonComponent::class)
interface AppDataModule
