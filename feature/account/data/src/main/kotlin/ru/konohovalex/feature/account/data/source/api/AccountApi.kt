package ru.konohovalex.feature.account.data.source.api

import retrofit2.http.Body
import retrofit2.http.POST
import ru.konohovalex.core.data.arch.source.api.Api
import ru.konohovalex.feature.account.data.auth.model.remote.AuthDataDto
import ru.konohovalex.feature.account.data.profile.model.remote.ProfileDto

internal interface AccountApi : Api {
    companion object {
        private const val ACCOUNT_URL = "v1/account"
    }

    @POST("${ACCOUNT_URL}/logIn")
    suspend fun logIn(@Body authData: AuthDataDto?): ProfileDto

    @POST("${ACCOUNT_URL}/{accountId}/delete")
    suspend fun deleteAccount(accountId: String): ProfileDto
}
