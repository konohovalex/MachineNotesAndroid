package ru.konohovalex.feature.preferences.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck
import ru.konohovalex.feature.preferences.data.repository.contract.PreferencesRepositoryContract
import ru.konohovalex.feature.preferences.data.repository.impl.PreferencesRepositoryImpl
import javax.inject.Singleton

@Module
@DisableInstallInCheck
internal interface PreferencesRepositoryModule {
    @Binds
    @Singleton
    fun bindPreferencesRepository(preferencesRepository: PreferencesRepositoryImpl): PreferencesRepositoryContract
}
