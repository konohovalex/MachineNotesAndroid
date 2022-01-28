package ru.konohovalex.feature.account.data.profile.repository.contract

import ru.konohovalex.core.utils.model.OperationResult
import ru.konohovalex.feature.account.data.auth.model.AuthData
import ru.konohovalex.feature.account.data.profile.model.Profile

interface ProfileRepositoryContract {
    suspend fun getProfile(): OperationResult<Profile?>
    suspend fun logIn(authData: AuthData?): OperationResult<Profile>
}
