package ru.konohovalex.feature.notes.data.source.api.provider

import ru.konohovalex.core.data.model.TypeAdapterParams
import ru.konohovalex.core.data.arch.provider.Provider
import ru.konohovalex.feature.notes.data.model.remote.NoteContentDto
import ru.konohovalex.feature.notes.data.source.api.NoteContentDtoJsonDeserializer
import javax.inject.Inject

internal class NotesGsonTypeAdapterProvider
@Inject constructor() : Provider<Nothing?, List<TypeAdapterParams>> {
    override fun provide(providerParams: Nothing?): List<TypeAdapterParams> = listOf(
        TypeAdapterParams(
            type = NoteContentDto::class.java,
            adapter = NoteContentDtoJsonDeserializer()
        )
    )
}
