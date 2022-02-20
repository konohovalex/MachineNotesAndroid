package ru.konohovalex.feature.account.data.di

import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck
import ru.konohovalex.feature.account.data.auth.di.AuthDataMappersModule
import ru.konohovalex.feature.account.data.profile.di.ProfileDataMappersModule

@Module(
    includes = [
        AuthDataMappersModule::class,
        ProfileDataMappersModule::class,
    ],
)
@DisableInstallInCheck
interface AccountDataMappersModule {
}
