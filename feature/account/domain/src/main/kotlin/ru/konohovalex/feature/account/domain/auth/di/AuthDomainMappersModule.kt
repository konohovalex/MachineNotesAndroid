package ru.konohovalex.feature.account.domain.auth.di

import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.account.data.auth.model.AuthData
import ru.konohovalex.feature.account.domain.auth.mapper.AuthDataDomainModelToAuthDataMapper
import ru.konohovalex.feature.account.domain.auth.model.AuthDataDomainModel

@Module
@DisableInstallInCheck
internal interface AuthDomainMappersModule {
    @Binds
    fun bindAuthDataDomainModelToAuthDataMapper(
        mapper: AuthDataDomainModelToAuthDataMapper,
    ): Mapper<AuthDataDomainModel, AuthData>
}
