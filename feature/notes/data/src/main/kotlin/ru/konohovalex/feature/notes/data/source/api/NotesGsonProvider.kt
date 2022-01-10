package ru.konohovalex.feature.notes.data.source.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import ru.konohovalex.core.data.model.TypeAdapterParams
import ru.konohovalex.core.data.source.contract.provider.Provider
import javax.inject.Inject

internal class NotesGsonProvider
@Inject constructor() : Provider<List<TypeAdapterParams>, Gson> {
    override fun provide(providerParams: List<TypeAdapterParams>): Gson = GsonBuilder()
        .apply {
            providerParams.forEach {
                registerTypeAdapter(it.type, it.adapter)
            }
        }
        .create()
}
