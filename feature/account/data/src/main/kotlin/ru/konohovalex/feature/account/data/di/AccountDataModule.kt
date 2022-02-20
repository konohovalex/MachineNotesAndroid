package ru.konohovalex.feature.account.data.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.konohovalex.feature.account.data.auth.di.AuthDataSharedPreferencesModule
import ru.konohovalex.feature.account.data.auth.di.AuthDataStorageModule
import ru.konohovalex.feature.account.data.auth.di.AuthTokenNetworkModule
import ru.konohovalex.feature.account.data.auth.di.AuthTokenRepositoryModule
import ru.konohovalex.feature.account.data.profile.di.ProfileDataStoreModule
import ru.konohovalex.feature.account.data.profile.di.ProfileStorageModule

@Module(
    includes = [
        AccountDataCoroutinesModule::class,

        AccountDataMappersModule::class,

        AccountDataGsonModule::class,

        AccountNetworkModule::class,

        AuthDataSharedPreferencesModule::class,
        AuthDataStorageModule::class,

        AuthTokenNetworkModule::class,
        AuthTokenRepositoryModule::class,

        ProfileDataStoreModule::class,
        ProfileStorageModule::class,

        AccountRepositoryModule::class,
    ],
)
@InstallIn(SingletonComponent::class)
internal interface AccountDataModule
