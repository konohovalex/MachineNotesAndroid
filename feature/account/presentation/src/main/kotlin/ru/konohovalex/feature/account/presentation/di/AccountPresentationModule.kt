package ru.konohovalex.feature.account.presentation.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.konohovalex.feature.account.presentation.auth.di.AuthPresentationMappersModule
import ru.konohovalex.feature.account.presentation.profile.di.ProfilePresentationMappersModule

@Module(
    includes = [
        AuthPresentationMappersModule::class,
        ProfilePresentationMappersModule::class,
    ],
)
@InstallIn(SingletonComponent::class)
internal interface AccountPresentationModule
