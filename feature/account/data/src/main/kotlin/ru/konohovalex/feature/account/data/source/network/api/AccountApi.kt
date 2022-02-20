package ru.konohovalex.feature.account.data.source.network.api

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import ru.konohovalex.core.data.arch.source.network.api.Api
import ru.konohovalex.feature.account.data.auth.model.remote.SingUpDataDto
import ru.konohovalex.feature.account.data.auth.model.remote.CredentialsDto
import ru.konohovalex.feature.account.data.auth.source.network.api.AuthTokenApi
import ru.konohovalex.feature.account.data.profile.model.remote.ProfileDto

internal interface AccountApi : AuthTokenApi, Api {
    companion object {
        const val ACCOUNT_URL = "v1/account"
    }

    @POST("$ACCOUNT_URL/signUp")
    suspend fun signUp(@Body singUpData: SingUpDataDto): ProfileDto

    @POST("$ACCOUNT_URL/signIn")
    suspend fun signIn(@Body credentialsDto: CredentialsDto): ProfileDto

    @DELETE(ACCOUNT_URL)
    suspend fun deleteAccount(): ProfileDto
}
