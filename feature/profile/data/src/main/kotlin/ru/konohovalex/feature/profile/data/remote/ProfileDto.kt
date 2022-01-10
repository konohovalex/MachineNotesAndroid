package ru.konohovalex.feature.profile.data.remote

import com.google.gson.annotations.SerializedName

data class ProfileDto(
    @SerializedName("name")
    val name: String?,
)
