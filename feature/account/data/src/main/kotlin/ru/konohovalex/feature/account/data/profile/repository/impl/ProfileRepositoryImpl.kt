package ru.konohovalex.feature.account.data.profile.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.konohovalex.core.utils.extension.withIo
import ru.konohovalex.core.utils.model.Mapper
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
    // tbd should there be safe update ops?
    override suspend fun observeProfile(): Flow<Profile?> =
        profileStorage.observeProfile()
            .map {
                it?.let(profileEntityToProfileMapper::invoke)
                // tbd if null, call logIn(null) before
            }

    override suspend fun logIn(authData: AuthData?): Profile = withIo {
        /*val profileDto = profileApi.logIn(authDataDto)*/
        val profileDto = ProfileDto(
            id = "0",
            name = authData?.userName?.value.orEmpty(),
            authToken = "12345",
            refreshToken = "54321",
        )
        val profileEntity = profileDtoToProfileEntityMapper.invoke(profileDto)
        val updatedProfileEntity = profileStorage.updateProfile(profileEntity)
        profileEntityToProfileMapper.invoke(updatedProfileEntity)
    }

    override suspend fun logOut(): Profile = logIn(null)

    // tbd
    // val authDataDto = profileApi.deleteAccount(getCurrentProfile.id.orEmpty())
    override suspend fun deleteAccount() = logIn(null)

    override suspend fun getCurrentProfile(): Profile? =
        profileStorage.getCurrentProfile()?.let(profileEntityToProfileMapper::invoke)
}
