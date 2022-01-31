package ru.konohovalex.feature.preferences.data.di

internal object Qualifiers {
    const val PREFERENCES_GSON_PROVIDER = "PreferencesGsonProvider"
    const val PREFERENCES_GSON = "PreferencesGson"
    const val PREFERENCES_DATA_STORE_PROVIDER = "PreferencesDataStoreProvider"
    const val PREFERENCES_DATA_STORE = "PreferencesDataStore"
    const val PREFERENCES_DATA_STORE_COROUTINE_SCOPE_PROVIDER = "PreferencesDataStoreCoroutineScopeProvider"
    const val PREFERENCES_DATA_STORE_COROUTINE_SCOPE = "PreferencesDataStoreCoroutineScope"
}
