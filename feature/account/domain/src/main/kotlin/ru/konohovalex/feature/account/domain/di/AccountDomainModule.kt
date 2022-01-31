package ru.konohovalex.feature.account.domain.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.konohovalex.feature.account.domain.auth.di.AuthDomainMappersModule
import ru.konohovalex.feature.account.domain.profile.di.ProfileDomainMappersModule

@Module(
    includes = [
        AuthDomainMappersModule::class,
        ProfileDomainMappersModule::class,
    ],
)
@InstallIn(SingletonComponent::class)
internal interface AccountDomainModule
