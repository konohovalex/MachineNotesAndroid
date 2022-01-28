package ru.konohovalex.feature.account.data.auth.model

import com.google.gson.annotations.SerializedName

data class AuthData(
    @SerializedName("userName")
    val userName: UserName,
    @SerializedName("password")
    val password: Password,
)
