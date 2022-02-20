package ru.konohovalex.feature.account.data.repository.contract

import kotlinx.coroutines.flow.Flow
import ru.konohovalex.feature.account.data.auth.model.AuthToken
import ru.konohovalex.feature.account.data.auth.model.Credentials
import ru.konohovalex.feature.account.data.auth.model.SignUpData
import ru.konohovalex.feature.account.data.profile.model.Profile

interface AccountRepositoryContract {
    suspend fun observeProfile(): Flow<Profile>
    suspend fun signUp(signUpData: SignUpData): Profile
    suspend fun signIn(credentials: Credentials): Profile
    suspend fun signOut(): Profile
    suspend fun deleteAccount(): Profile
}
