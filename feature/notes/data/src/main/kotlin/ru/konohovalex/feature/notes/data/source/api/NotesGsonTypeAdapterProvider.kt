package ru.konohovalex.feature.notes.data.source.api

import ru.konohovalex.core.data.model.TypeAdapterParams
import ru.konohovalex.core.data.source.contract.provider.Provider
import ru.konohovalex.feature.notes.data.model.remote.NoteContentDto
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
