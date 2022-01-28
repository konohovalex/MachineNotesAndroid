package ru.konohovalex.feature.account.presentation.auth.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.account.domain.auth.model.AuthDataDomainModel
import ru.konohovalex.feature.account.presentation.auth.mapper.AuthDataUiModelToAuthDataDomainModelMapper
import ru.konohovalex.feature.account.presentation.auth.model.AuthDataUiModel

@Module
@InstallIn(SingletonComponent::class)
internal interface AuthPresentationMappersModule {
    @Binds
    fun bindAuthDataUiModelToAuthDataDomainModelMapper(
        mapper: AuthDataUiModelToAuthDataDomainModelMapper,
    ): Mapper<AuthDataUiModel, AuthDataDomainModel>
}
