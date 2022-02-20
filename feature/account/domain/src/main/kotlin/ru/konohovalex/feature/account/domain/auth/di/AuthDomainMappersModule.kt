package ru.konohovalex.feature.account.domain.auth.di

import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.account.data.auth.model.SignUpData
import ru.konohovalex.feature.account.domain.auth.mapper.SignUpDataDomainModelToSignUpDataMapper
import ru.konohovalex.feature.account.domain.auth.model.SignUpDataDomainModel

@Module
@DisableInstallInCheck
internal interface AuthDomainMappersModule {
    @Binds
    fun bindSignUpDataDomainModelToSignUpDataMapper(
        mapper: SignUpDataDomainModelToSignUpDataMapper,
    ): Mapper<SignUpDataDomainModel, SignUpData>
}
