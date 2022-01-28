package ru.konohovalex.feature.account.domain.auth.model

data class AuthDataDomainModel(
    val userNameDomainModel: UserNameDomainModel,
    val passwordDomainModel: PasswordDomainModel,
)
