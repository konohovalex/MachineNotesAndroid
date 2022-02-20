package ru.konohovalex.feature.account.data.di

internal object Qualifiers {
    const val ACCOUNT_DATA_COROUTINE_SCOPE = "AccountDataCoroutineScope"

    const val ACCOUNT_DATA_GSON_PROVIDER = "AccountDataGsonProvider"
    const val ACCOUNT_DATA_GSON = "AccountDataGson"
    const val ACCOUNT_DATA_GSON_CONVERTER_FACTORY = "AccountDataGsonConverterFactory"

    const val ACCOUNT_OK_HTTP_CLIENT_PROVIDER = "AccountOkHttpClientProvider"
    const val ACCOUNT_OK_HTTP_CLIENT = "AccountOkHttpClient"

    const val AUTH_TOKEN_OK_HTTP_CLIENT_PROVIDER = "AuthTokenOkHttpClientProvider"
    const val AUTH_TOKEN_OK_HTTP_CLIENT = "AuthTokenOkHttpClient"

    const val AUTH_DATA_SHARED_PREFERENCES_PROVIDER = "AuthDataSharedPreferencesProvider"
    const val AUTH_DATA_SHARED_PREFERENCES = "AuthDataSharedPreferences"

    const val PROFILE_DATA_STORE_PROVIDER = "ProfileDataStoreProvider"
    const val PROFILE_PREFERENCES_DATA_STORE = "ProfilePreferencesDataStore"
}
