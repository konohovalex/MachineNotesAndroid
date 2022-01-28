package ru.konohovalex.feature.account.data.profile.repository.impl

import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.core.utils.model.OperationResult
import ru.konohovalex.feature.account.data.auth.model.AuthData
import ru.konohovalex.feature.account.data.auth.model.remote.AuthDataDto
import ru.konohovalex.feature.account.data.profile.model.Profile
import ru.konohovalex.feature.account.data.profile.model.entity.ProfileEntity
import ru.konohovalex.feature.account.data.profile.model.remote.ProfileDto
import ru.konohovalex.feature.account.data.profile.repository.contract.ProfileRepositoryContract
import ru.konohovalex.feature.account.data.profile.source.api.ProfileApi
import ru.konohovalex.feature.account.data.profile.source.storage.contract.ProfileStorageContract
import javax.inject.Inject

internal class ProfileRepositoryImpl
@Inject constructor(
    private val profileStorage: ProfileStorageContract,
    private val profileApi: ProfileApi,
    private val profileEntityToProfileMapper: Mapper<ProfileEntity, Profile>,
    private val profileDtoToProfileEntityMapper: Mapper<ProfileDto, ProfileEntity>,
    private val authDataToAuthDataDtoMapper: Mapper<AuthData, AuthDataDto>,
) : ProfileRepositoryContract {
    override suspend fun getProfile(): OperationResult<Profile?> {
        val profileEntity = profileStorage.getProfile()
        val profile = profileEntity?.let(profileEntityToProfileMapper::invoke)
        return OperationResult.Success(profile)
    }

    override suspend fun logIn(authData: AuthData?): OperationResult<Profile> {
        /*val authDataDto = authData?.let(authDataToAuthDataDtoMapper::invoke)
        val profileDto = profileApi.logIn(authDataDto)*/
        val profileDto = ProfileDto(
            id = "0",
            name = authData?.userName?.value.orEmpty(),
            authToken = "12345",
            refreshToken = "54321",
        )
        val profileEntity = profileStorage.updateProfile(profileDtoToProfileEntityMapper.invoke(profileDto))
        val profile = profileEntityToProfileMapper.invoke(profileEntity)
        return OperationResult.Success(profile)
    }
}
