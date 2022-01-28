package ru.konohovalex.feature.account.data.profile.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.konohovalex.feature.account.data.profile.repository.contract.ProfileRepositoryContract
import ru.konohovalex.feature.account.data.profile.repository.impl.ProfileRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface ProfileRepositoryModule {
    @Binds
    @Singleton
    fun bindProfileRepository(profileRepository: ProfileRepositoryImpl): ProfileRepositoryContract
}
