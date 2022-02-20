package ru.konohovalex.feature.account.domain.di

import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck
import ru.konohovalex.feature.account.domain.auth.di.AuthDomainMappersModule
import ru.konohovalex.feature.account.domain.profile.di.ProfileDomainMappersModule

@Module(
    includes = [
        AuthDomainMappersModule::class,
        ProfileDomainMappersModule::class,
    ],
)
@DisableInstallInCheck
interface AccountDomainMappersModule
