package ru.konohovalex.feature.account.data.profile.model.remote

import com.google.gson.annotations.SerializedName
import ru.konohovalex.feature.account.data.auth.model.remote.AuthTokenDto

internal data class ProfileDto(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("userName")
    val userName: String?,
    @SerializedName("authToken")
    val authTokenDto: AuthTokenDto,
)
