package ru.konohovalex.feature.account.data.auth.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.account.data.auth.mapper.AuthDataToAuthDataDtoMapper
import ru.konohovalex.feature.account.data.auth.model.AuthData
import ru.konohovalex.feature.account.data.auth.model.remote.AuthDataDto

@Module
@InstallIn(SingletonComponent::class)
internal interface AuthDataMappersModule {
    @Binds
    fun bindAuthDataToAuthDataDtoMapper(
        mapper: AuthDataToAuthDataDtoMapper,
    ): Mapper<AuthData, AuthDataDto>
}
