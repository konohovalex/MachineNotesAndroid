package ru.konohovalex.feature.account.data.repository.contract

import kotlinx.coroutines.flow.Flow
import ru.konohovalex.feature.account.data.auth.model.AuthData
import ru.konohovalex.feature.account.data.profile.model.Profile

interface AccountRepositoryContract {
    suspend fun observeProfile(): Flow<Profile>
    suspend fun logIn(authData: AuthData?): Profile
    suspend fun logOut(): Profile
    suspend fun deleteAccount(): Profile
}
