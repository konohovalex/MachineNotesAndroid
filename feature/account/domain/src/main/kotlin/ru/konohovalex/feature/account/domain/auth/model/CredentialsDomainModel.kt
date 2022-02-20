package ru.konohovalex.feature.account.domain.auth.model

data class CredentialsDomainModel(
    val userNameDomainModel: UserNameDomainModel,
    val passwordDomainModel: PasswordDomainModel,
)
