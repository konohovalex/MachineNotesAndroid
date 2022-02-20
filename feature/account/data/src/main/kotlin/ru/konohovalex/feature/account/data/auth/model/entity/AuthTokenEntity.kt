package ru.konohovalex.feature.account.data.auth.model.entity

data class AuthTokenEntity(
    val accessToken: String,
    val refreshToken: String,
)
