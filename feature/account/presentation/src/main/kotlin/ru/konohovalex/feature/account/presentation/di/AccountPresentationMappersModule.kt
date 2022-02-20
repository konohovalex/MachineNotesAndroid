package ru.konohovalex.feature.account.presentation.di

import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck
import ru.konohovalex.feature.account.presentation.auth.di.AuthPresentationMappersModule
import ru.konohovalex.feature.account.presentation.profile.di.ProfilePresentationMappersModule

@Module(
    includes = [
        AuthPresentationMappersModule::class,
        ProfilePresentationMappersModule::class,
    ],
)
@DisableInstallInCheck
internal interface AccountPresentationMappersModule
