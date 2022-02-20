package ru.konohovalex.feature.account.data.auth.source.network.interceptor

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import ru.konohovalex.feature.account.data.auth.repository.contract.AuthTokenRepositoryContract
import ru.konohovalex.feature.account.data.auth.utils.addAccessToken
import ru.konohovalex.feature.account.data.auth.utils.isAuthTokenRefreshAttemptsAmountExceeded
import ru.konohovalex.feature.account.data.auth.utils.isRequestContainedAuthToken
import javax.inject.Inject

class BearerAuthenticator
@Inject constructor(private val authTokenRepository: AuthTokenRepositoryContract) : Authenticator {
    override fun authenticate(
        route: Route?,
        response: Response,
    ): Request? {
        val authToken = authTokenRepository.getAuthToken()

        return when {
            !response.isRequestContainedAuthToken() || authToken == null -> {
                null
            }
            response.isAuthTokenRefreshAttemptsAmountExceeded() -> {
                // tbd logout and navigate to auth
                null
            }
            else -> {
                synchronized(this) {
                    val maybeUpdatedAuthToken = authTokenRepository.refreshAuthToken() ?: return null

                    /** Another thread can update AuthToken */
                    if (authToken != maybeUpdatedAuthToken) return response.request().addAccessToken(maybeUpdatedAuthToken.accessToken)

                    try {
                        authTokenRepository.refreshAuthToken()
                            ?.accessToken
                            ?.let(response.request()::addAccessToken)
                    }
                    catch (throwable: Throwable) {
                        // tbd logout and navigate to auth
                        null
                    }
                }
            }
        }
    }
}
