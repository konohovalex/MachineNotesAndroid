package ru.konohovalex.feature.account.data.auth.source.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import ru.konohovalex.feature.account.data.auth.source.storage.contract.AuthDataStorageContract
import ru.konohovalex.feature.account.data.auth.utils.addAccessToken
import javax.inject.Inject

class AuthInterceptor
@Inject constructor(private val authDataStorageContract: AuthDataStorageContract) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request()
                .addAccessToken(authDataStorageContract.getAuthToken()?.accessToken.toString())
        )
    }
}
