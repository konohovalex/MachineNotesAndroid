package ru.konohovalex.feature.account.data.profile.repository.contract

import kotlinx.coroutines.flow.Flow
import ru.konohovalex.feature.account.data.auth.model.AuthData
import ru.konohovalex.feature.account.data.profile.model.Profile

interface ProfileRepositoryContract {
    suspend fun observeProfile(): Flow<Profile?>
    suspend fun getCurrentProfile(): Profile?
    suspend fun logIn(authData: AuthData?): Profile
    suspend fun logOut(): Profile
    suspend fun deleteAccount(): Profile
}
