package ru.konohovalex.feature.account.data.auth.di

import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.account.data.auth.mapper.SignUpDataToSignUpDataDtoMapper
import ru.konohovalex.feature.account.data.auth.model.SignUpData
import ru.konohovalex.feature.account.data.auth.model.remote.SingUpDataDto

@Module
@DisableInstallInCheck
internal interface AuthDataMappersModule {
    @Binds
    fun bindSignUpDataToSignUpDataDtoMapper(
        mapper: SignUpDataToSignUpDataDtoMapper,
    ): Mapper<SignUpData, SingUpDataDto>
}
