package ru.konohovalex.machinenotes.app.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck
import ru.konohovalex.machinenotes.app.data.repository.contract.AppPreferencesRepositoryContract
import ru.konohovalex.machinenotes.app.data.repository.impl.AppPreferencesRepositoryImpl
import javax.inject.Singleton

@Module
@DisableInstallInCheck
internal interface AppPreferencesRepositoryModule {
    @Binds
    @Singleton
    fun bindAppPreferencesRepository(appPreferencesRepository: AppPreferencesRepositoryImpl): AppPreferencesRepositoryContract
}
