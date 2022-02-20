package ru.konohovalex.feature.account.data.auth.source.storage.impl

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.konohovalex.core.utils.extension.fromJson
import ru.konohovalex.feature.account.data.auth.model.entity.AuthTokenEntity
import ru.konohovalex.feature.account.data.auth.source.storage.contract.AuthDataStorageContract
import javax.inject.Inject

internal class AuthDataStorageImpl
@Inject constructor(
    private val authDataSharedPreferences: SharedPreferences,
    private val gson: Gson,
) : AuthDataStorageContract {
    companion object {
        private const val KEY_AUTH_TOKEN = "key_auth_token"
        private const val KEY_USER_ID = "key_user_id"
    }

    override fun getAuthToken(): AuthTokenEntity? =
        authDataSharedPreferences.getString(KEY_AUTH_TOKEN, null)
            ?.let(::mapJsonToAuthTokenEntity)

    override fun updateAuthToken(authToken: AuthTokenEntity): AuthTokenEntity {
        authDataSharedPreferences.edit()
            .putString(KEY_AUTH_TOKEN, gson.toJson(authToken))
            .apply()

        val updatedAuthToken = getAuthToken()

        return if (updatedAuthToken == authToken) updatedAuthToken else authTokenUpdateError()
    }

    override fun getUserId(): String? = authDataSharedPreferences.getString(KEY_USER_ID, null)

    override fun updateUserId(userId: String): String {
        authDataSharedPreferences.edit()
            .putString(KEY_USER_ID, userId)
            .apply()

        val updatedUserId = getUserId()

        return if (updatedUserId == userId) updatedUserId else userIdUpdateError()
    }

    private fun mapJsonToAuthTokenEntity(authTokenEntityJson: String): AuthTokenEntity =
        gson.fromJson<AuthTokenEntity>(authTokenEntityJson)

    private fun authTokenUpdateError(): Nothing =
        throw IllegalStateException(
            "Get unexpected ${AuthTokenEntity::class.simpleName} value after updating $authDataSharedPreferences",
        )

    private fun userIdUpdateError(): Nothing =
        throw IllegalStateException(
            "Get unexpected userId value after updating $authDataSharedPreferences",
        )
}
