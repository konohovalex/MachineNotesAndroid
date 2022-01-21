package ru.konohovalex.feature.notes.data.source.api.provider

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.konohovalex.core.data.Constants
import ru.konohovalex.core.data.arch.source.api.provider.ApiProvider
import ru.konohovalex.feature.notes.data.source.api.NotesApi
import javax.inject.Inject

internal class NotesApiProvider
@Inject constructor() : ApiProvider<GsonConverterFactory, NotesApi> {
    override fun provide(providerParams: GsonConverterFactory): NotesApi = Retrofit.Builder()
        .baseUrl(Constants.BASE_NOTES_API_URL)
        .addConverterFactory(providerParams)
        .build()
        .create(NotesApi::class.java)
}
