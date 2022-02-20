package ru.konohovalex.feature.notes.data.source.network.api.provider

import retrofit2.Retrofit
import ru.konohovalex.core.data.Constants
import ru.konohovalex.core.data.arch.source.network.api.provider.ApiProvider
import ru.konohovalex.feature.notes.data.source.network.api.NotesApi
import javax.inject.Inject

internal class NotesApiProvider
@Inject constructor() : ApiProvider<NotesApiProviderParams, NotesApi> {
    override fun provide(providerParams: NotesApiProviderParams): NotesApi = Retrofit.Builder()
        .baseUrl(Constants.BASE_API_URL)
        .client(providerParams.okHttpClient)
        .addConverterFactory(providerParams.gsonConverterFactory)
        .build()
        .create(NotesApi::class.java)
}
