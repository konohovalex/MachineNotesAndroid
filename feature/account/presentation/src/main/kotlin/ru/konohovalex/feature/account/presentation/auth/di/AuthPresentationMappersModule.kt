package ru.konohovalex.feature.account.presentation.auth.di

import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.account.domain.auth.model.SignUpDataDomainModel
import ru.konohovalex.feature.account.presentation.auth.mapper.CredentialsUiModelToSignUpDataDomainModelMapper
import ru.konohovalex.feature.account.presentation.auth.model.CredentialsUiModel

@Module
@DisableInstallInCheck
internal interface AuthPresentationMappersModule {
    @Binds
    fun bindCredentialsUiModelToSignUpDataDomainModelMapper(
        mapper: CredentialsUiModelToSignUpDataDomainModelMapper,
    ): Mapper<CredentialsUiModel, SignUpDataDomainModel>
}
