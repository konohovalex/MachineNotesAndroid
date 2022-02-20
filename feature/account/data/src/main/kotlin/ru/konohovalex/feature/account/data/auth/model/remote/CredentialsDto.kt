package ru.konohovalex.feature.account.data.auth.model.remote

import com.google.gson.annotations.SerializedName

data class CredentialsDto(
    @SerializedName("userName")
    val userName: String,
    @SerializedName("password")
    val password: String,
)
