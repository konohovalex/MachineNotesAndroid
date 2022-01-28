package ru.konohovalex.feature.account.data.profile.model.remote

import com.google.gson.annotations.SerializedName

// tbd this is dirty implementation
internal data class ProfileDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("authToken")
    val authToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String,
)
