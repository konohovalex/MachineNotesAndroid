package ru.konohovalex.feature.preferences.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.konohovalex.feature.preferences.data.repository.contract.PreferencesRepository
import ru.konohovalex.feature.preferences.data.repository.impl.PreferencesRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface PreferencesRepositoryModule {
    @Binds
    @Singleton
    fun bindPreferencesRepository(preferencesRepository: PreferencesRepositoryImpl): PreferencesRepository
}
