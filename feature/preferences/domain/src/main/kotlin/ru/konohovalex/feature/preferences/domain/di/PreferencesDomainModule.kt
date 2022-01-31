package ru.konohovalex.feature.preferences.domain.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(
    includes = [
        PreferencesDomainMappersModule::class,
    ],
)
@InstallIn(SingletonComponent::class)
internal interface PreferencesDomainModule
