package ru.konohovalex.feature.account.data.auth.repository.contract

import ru.konohovalex.feature.account.data.auth.model.AuthToken

interface AuthTokenRepositoryContract {
    fun getAuthToken(): AuthToken?
    fun refreshAuthToken(): AuthToken?
}
