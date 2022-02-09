package ru.konohovalex.feature.account.data.di

internal object Qualifiers {
    const val ACCOUNT_DATA_COROUTINE_SCOPE = "AccountDataCoroutineScope"

    const val ACCOUNT_GSON_PROVIDER = "AccountGsonProvider"
    const val ACCOUNT_GSON = "AccountGson"
    const val ACCOUNT_GSON_CONVERTER_FACTORY = "AccountGsonConverterFactory"

    const val PROFILE_GSON_PROVIDER = "ProfileGsonProvider"
    const val PROFILE_GSON = "ProfileGson"
    const val PROFILE_DATA_STORE_PROVIDER = "ProfileDataStoreProvider"
    const val PROFILE_PREFERENCES_DATA_STORE = "ProfilePreferencesDataStore"
}
