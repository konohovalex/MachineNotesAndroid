package ru.konohovalex.feature.account.data.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.konohovalex.core.utils.extension.withIo
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.account.data.auth.model.Credentials
import ru.konohovalex.feature.account.data.auth.model.SignUpData
import ru.konohovalex.feature.account.data.auth.model.entity.AuthTokenEntity
import ru.konohovalex.feature.account.data.auth.model.remote.AuthTokenDto
import ru.konohovalex.feature.account.data.auth.model.remote.CredentialsDto
import ru.konohovalex.feature.account.data.auth.model.remote.SingUpDataDto
import ru.konohovalex.feature.account.data.auth.source.storage.contract.AuthDataStorageContract
import ru.konohovalex.feature.account.data.profile.model.Profile
import ru.konohovalex.feature.account.data.profile.model.entity.ProfileEntity
import ru.konohovalex.feature.account.data.profile.model.remote.ProfileDto
import ru.konohovalex.feature.account.data.profile.source.storage.contract.ProfileStorageContract
import ru.konohovalex.feature.account.data.repository.contract.AccountRepositoryContract
import ru.konohovalex.feature.account.data.source.network.api.AccountApi
import javax.inject.Inject

internal class AccountRepositoryImpl
@Inject constructor(
    private val authDataStorageContract: AuthDataStorageContract,
    private val profileStorage: ProfileStorageContract,
    private val accountApi: AccountApi,
    private val profileEntityToProfileMapper: Mapper<ProfileEntity, Profile>,
    private val profileDtoToProfileEntityMapper: Mapper<ProfileDto, ProfileEntity>,
    private val singUpDataToSignUpDataDtoMapper: Mapper<SignUpData, SingUpDataDto>,
) : AccountRepositoryContract {
    override suspend fun observeProfile(): Flow<Profile> = withIo {
        profileStorage.observeProfile()
            .map {
                profileEntityToProfileMapper.invoke(it)
            }
    }

    override suspend fun signUp(signUpData: SignUpData): Profile = withIo {
        val signUpDataDto = singUpDataToSignUpDataDtoMapper.invoke(signUpData)
        val profileDto = accountApi.signUp(signUpDataDto)
        updateProfile(profileDto)
    }

    override suspend fun signIn(credentials: Credentials): Profile = withIo {
        val credentialsDto = CredentialsDto(
            userName = credentials.userName.value,
            password = credentials.password.value,
        )
        val profileDto = accountApi.signIn(credentialsDto)
        updateProfile(profileDto)
    }

    override suspend fun signOut(): Profile = signUp(SignUpData.empty())

    override suspend fun deleteAccount(): Profile {
        val profileDto = accountApi.deleteAccount()
        return updateProfile(profileDto)
    }

    private suspend fun updateProfile(profileDto: ProfileDto): Profile {
        val profileEntity = profileDtoToProfileEntityMapper.invoke(profileDto)
        val updatedProfileEntity = profileStorage.updateProfile(profileEntity)
        authDataStorageContract.updateAuthToken(mapAuthTokenDtoToAuthTokenEntity(profileDto.authTokenDto))
        authDataStorageContract.updateUserId(profileDto.userId)
        return profileEntityToProfileMapper.invoke(updatedProfileEntity)
    }

    private fun mapAuthTokenDtoToAuthTokenEntity(authTokenDto: AuthTokenDto): AuthTokenEntity =
        AuthTokenEntity(authTokenDto.accessToken, authTokenDto.refreshToken)
}
