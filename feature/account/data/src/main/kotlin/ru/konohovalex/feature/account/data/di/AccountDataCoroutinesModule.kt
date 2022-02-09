package ru.konohovalex.feature.account.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Named

@Module
@DisableInstallInCheck
class AccountDataCoroutinesModule {
    @Provides
    @Named(Qualifiers.ACCOUNT_DATA_COROUTINE_SCOPE)
    fun provideCoroutineScope(): CoroutineScope = CoroutineScope(context = SupervisorJob() + Dispatchers.IO)
}
