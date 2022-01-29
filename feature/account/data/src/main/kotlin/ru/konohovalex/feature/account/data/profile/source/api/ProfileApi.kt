package ru.konohovalex.feature.account.data.profile.source.api

import retrofit2.http.Body
import retrofit2.http.POST
import ru.konohovalex.core.data.arch.source.api.Api
import ru.konohovalex.feature.account.data.auth.model.remote.AuthDataDto
import ru.konohovalex.feature.account.data.profile.model.remote.ProfileDto

internal interface ProfileApi : Api {
    companion object {
        private const val PROFILE_URL = "v1/profile"
    }

    @POST("${PROFILE_URL}/logIn")
    suspend fun logIn(@Body authData: AuthDataDto?): ProfileDto

    @POST("${PROFILE_URL}/{accountId}/delete")
    suspend fun deleteAccount(accountId: String): ProfileDto
}
