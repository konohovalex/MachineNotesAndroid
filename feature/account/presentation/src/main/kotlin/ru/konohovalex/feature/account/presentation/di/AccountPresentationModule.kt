package ru.konohovalex.feature.account.presentation.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(
    includes = [
        AccountPresentationMappersModule::class,
    ],
)
@InstallIn(SingletonComponent::class)
internal interface AccountPresentationModule
