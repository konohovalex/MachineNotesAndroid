package ru.konohovalex.feature.account.data.auth.repository.impl

import ru.konohovalex.feature.account.data.auth.model.AuthToken
import ru.konohovalex.feature.account.data.auth.model.entity.AuthTokenEntity
import ru.konohovalex.feature.account.data.auth.model.remote.AuthTokenDto
import ru.konohovalex.feature.account.data.auth.repository.contract.AuthTokenRepositoryContract
import ru.konohovalex.feature.account.data.auth.source.network.api.AuthTokenApi
import ru.konohovalex.feature.account.data.auth.source.storage.contract.AuthDataStorageContract
import javax.inject.Inject

internal class AuthTokenRepositoryImpl
@Inject constructor(
    private val authTokenApi: AuthTokenApi,
    private val authDataStorageContract: AuthDataStorageContract,
) : AuthTokenRepositoryContract {
    override fun getAuthToken(): AuthToken? =
        authDataStorageContract.getAuthToken()
            ?.let(::mapAuthTokenEntityToAuthToken)

    override fun refreshAuthToken(): AuthToken? =
        authDataStorageContract.getAuthToken()
            ?.let {
                authTokenApi.refreshToken(it.refreshToken)
                    .execute()
                    .body()
                    ?.let(::mapAuthTokenDtoToAuthTokenEntity)
                    ?.let(authDataStorageContract::updateAuthToken)
                    ?.let(::mapAuthTokenEntityToAuthToken)
            }

    private fun mapAuthTokenEntityToAuthToken(authTokenEntity: AuthTokenEntity): AuthToken =
        AuthToken(authTokenEntity.accessToken, authTokenEntity.refreshToken)

    private fun mapAuthTokenDtoToAuthTokenEntity(authTokenDto: AuthTokenDto): AuthTokenEntity =
        AuthTokenEntity(authTokenDto.accessToken, authTokenDto.refreshToken)
}
