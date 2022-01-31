package ru.konohovalex.feature.account.data.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.konohovalex.feature.account.data.auth.di.AuthDataMappersModule
import ru.konohovalex.feature.account.data.profile.di.ProfileDataMappersModule
import ru.konohovalex.feature.account.data.profile.di.ProfileGsonModule
import ru.konohovalex.feature.account.data.profile.di.ProfileStorageModule

@Module(
    includes = [
        AccountApiModule::class,
        AccountGsonModule::class,
        AccountRepositoryModule::class,

        AuthDataMappersModule::class,

        ProfileDataMappersModule::class,
        ProfileGsonModule::class,
        ProfileStorageModule::class,
    ],
)
@InstallIn(SingletonComponent::class)
internal interface AccountDataModule
