package ru.konohovalex.feature.notes.data.di

internal object Qualifiers {
    const val NOTES_DATA_COROUTINES_SCOPE = "NotesDataCoroutineScope"

    const val NOTES_DATA_GSON_PROVIDER = "NotesDataGsonProvider"
    const val NOTES_DATA_GSON = "NotesDataGson"
    const val NOTES_DATA_GSON_CONVERTER_FACTORY = "NotesDataGsonConverterFactory"

    const val NOTES_OK_HTTP_CLIENT_PROVIDER = "NotesOkHttpClientProvider"
    const val NOTES_OK_HTTP_CLIENT = "NotesOkHttpClient"
}
