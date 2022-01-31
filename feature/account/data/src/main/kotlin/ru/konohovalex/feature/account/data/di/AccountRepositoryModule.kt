package ru.konohovalex.feature.account.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck
import ru.konohovalex.feature.account.data.repository.contract.AccountRepositoryContract
import ru.konohovalex.feature.account.data.repository.impl.AccountRepositoryImpl
import javax.inject.Singleton

@Module
@DisableInstallInCheck
internal interface AccountRepositoryModule {
    @Binds
    @Singleton
    fun bindAccountRepository(accountRepository: AccountRepositoryImpl): AccountRepositoryContract
}
