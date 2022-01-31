package ru.konohovalex.feature.notes.data.source.provider

import com.google.gson.Gson
import ru.konohovalex.core.data.arch.provider.Provider
import ru.konohovalex.core.data.model.TypeAdapterParams
import ru.konohovalex.core.data.extension.createGsonWithTypeAdapters
import ru.konohovalex.feature.notes.data.model.remote.NoteContentDto
import ru.konohovalex.feature.notes.data.source.api.NoteContentDtoTypeAdapter
import javax.inject.Inject

internal class NotesGsonProvider
@Inject constructor() : Provider<Nothing?, Gson> {
    override fun provide(providerParams: Nothing?): Gson = createGsonWithTypeAdapters(
        listOf(
            TypeAdapterParams(
                type = NoteContentDto::class.java,
                typeAdapter = NoteContentDtoTypeAdapter(),
            )
        )
    )
}
