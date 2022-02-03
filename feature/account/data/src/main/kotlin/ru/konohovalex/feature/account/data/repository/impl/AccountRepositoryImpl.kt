package ru.konohovalex.feature.account.data.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.konohovalex.core.utils.extension.withIo
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.account.data.auth.model.AuthData
import ru.konohovalex.feature.account.data.auth.model.remote.AuthDataDto
import ru.konohovalex.feature.account.data.profile.model.Profile
import ru.konohovalex.feature.account.data.profile.model.entity.ProfileEntity
import ru.konohovalex.feature.account.data.profile.model.remote.ProfileDto
import ru.konohovalex.feature.account.data.profile.source.storage.contract.ProfileStorageContract
import ru.konohovalex.feature.account.data.repository.contract.AccountRepositoryContract
import ru.konohovalex.feature.account.data.source.api.AccountApi
import javax.inject.Inject

internal class AccountRepositoryImpl
@Inject constructor(
    private val profileStorage: ProfileStorageContract,
    private val accountApi: AccountApi,
    private val profileEntityToProfileMapper: Mapper<ProfileEntity, Profile>,
    private val profileDtoToProfileEntityMapper: Mapper<ProfileDto, ProfileEntity>,
    private val authDataToAuthDataDtoMapper: Mapper<AuthData, AuthDataDto>,
) : AccountRepositoryContract {
    // tbd should there be safe update ops?
    override suspend fun observeProfile(): Flow<Profile> = withIo {
        profileStorage.observeProfile()
            .map {
                it?.let(profileEntityToProfileMapper::invoke)
                    ?: logIn(null)
            }
    }

    override suspend fun logIn(authData: AuthData?): Profile = withIo {
        /*val profileDto = accountApi.logIn(authDataDto)*/
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
    // val authDataDto = accountApi.deleteAccount(getCurrentProfile.id.orEmpty())
    override suspend fun deleteAccount() = logIn(null)
}
