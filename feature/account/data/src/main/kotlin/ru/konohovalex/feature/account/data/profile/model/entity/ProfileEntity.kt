package ru.konohovalex.feature.account.data.profile.model.entity

// tbd this is dirty implementation
internal data class ProfileEntity(
    val id: String,
    val name: String,
    val authToken: String,
    val refreshToken: String,
)
