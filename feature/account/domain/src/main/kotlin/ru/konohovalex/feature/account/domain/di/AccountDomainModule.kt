package ru.konohovalex.feature.account.domain.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(
    includes = [
        AccountDomainMappersModule::class,
    ],
)
@InstallIn(SingletonComponent::class)
internal interface AccountDomainModule
