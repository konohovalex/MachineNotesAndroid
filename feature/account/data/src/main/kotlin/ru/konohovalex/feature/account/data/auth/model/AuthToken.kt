package ru.konohovalex.feature.account.data.auth.model

data class AuthToken(
    val accessToken: String,
    val refreshToken: String,
)
