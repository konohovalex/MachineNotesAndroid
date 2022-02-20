package ru.konohovalex.feature.account.data.auth.model

data class SignUpData(val credentials: Credentials?) {
    companion object {
        fun empty() = SignUpData(null)
    }
}
