package ru.konohovalex.feature.account.data.auth.di

import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck
import ru.konohovalex.feature.account.data.auth.repository.contract.AuthTokenRepositoryContract
import ru.konohovalex.feature.account.data.auth.repository.impl.AuthTokenRepositoryImpl
import ru.konohovalex.feature.account.data.repository.contract.AccountRepositoryContract
import ru.konohovalex.feature.account.data.repository.impl.AccountRepositoryImpl
import javax.inject.Singleton

@Module
@DisableInstallInCheck
internal interface AuthTokenRepositoryModule {
    @Binds
    @Singleton
    fun bindAuthTokenRepository(authTokenRepository: AuthTokenRepositoryImpl): AuthTokenRepositoryContract
}
