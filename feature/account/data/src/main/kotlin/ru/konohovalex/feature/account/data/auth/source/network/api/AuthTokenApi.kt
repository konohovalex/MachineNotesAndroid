package ru.konohovalex.feature.account.data.auth.source.network.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import ru.konohovalex.core.data.arch.source.network.api.Api
import ru.konohovalex.feature.account.data.auth.model.remote.AuthTokenDto
import ru.konohovalex.feature.account.data.source.network.api.AccountApi

internal interface AuthTokenApi : Api {
    @POST("${AccountApi.ACCOUNT_URL}/refreshToken")
    fun refreshToken(@Body refreshToken: String): Call<AuthTokenDto>
}
