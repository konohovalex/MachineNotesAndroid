package ru.konohovalex.feature.account.data.auth.model.remote

import com.google.gson.annotations.SerializedName

data class AuthTokenDto(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String,
)
