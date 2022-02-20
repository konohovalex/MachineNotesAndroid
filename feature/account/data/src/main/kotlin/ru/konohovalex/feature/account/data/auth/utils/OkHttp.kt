package ru.konohovalex.feature.account.data.auth.utils

import okhttp3.Request
import okhttp3.Response

private const val AUTHORIZATION_HEADER_KEY = "Authorization"
private const val BEARER_PREFIX = "Bearer "
private const val MAX_AUTH_TOKEN_REFRESH_ATTEMPTS_AMOUNT = 5

fun Request.addAccessToken(accessToken: String): Request = newBuilder()
    .header(AUTHORIZATION_HEADER_KEY, "$BEARER_PREFIX$accessToken")
    .build()

fun Response.isRequestContainedAuthToken(): Boolean =
    request().header(AUTHORIZATION_HEADER_KEY)
        ?.takeIf {
            it.startsWith(BEARER_PREFIX)
        } != null

fun Response.priorResponseCount(): Int {
    var response = this
    var result = 1
    while (response.priorResponse()?.also { response = it } != null) result++
    return result
}

fun Response.isAuthTokenRefreshAttemptsAmountExceeded(): Boolean =
    priorResponseCount() > MAX_AUTH_TOKEN_REFRESH_ATTEMPTS_AMOUNT
