package ru.konohovalex.feature.account.data.auth.model.remote

import com.google.gson.annotations.SerializedName

internal data class SingUpDataDto(
    @SerializedName("credentials")
    val credentialsDto: CredentialsDto?,
)
