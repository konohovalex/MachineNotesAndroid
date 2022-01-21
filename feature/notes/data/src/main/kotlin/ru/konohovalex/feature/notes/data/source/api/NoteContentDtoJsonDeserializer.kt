package ru.konohovalex.feature.notes.data.source.api

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import ru.konohovalex.core.utils.fromJson
import ru.konohovalex.feature.notes.data.model.remote.NoteContentDto
import ru.konohovalex.feature.notes.data.model.remote.NoteContentDtoType
import java.lang.reflect.Type

internal class NoteContentDtoJsonDeserializer : JsonDeserializer<NoteContentDto> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): NoteContentDto? {
        val gson = GsonBuilder().create()

        val jsonObject = json?.asJsonObject
        val typeSerializedName = jsonObject?.keySet()?.toList()?.getOrNull(0)
        val noteContentDtoJson = jsonObject?.get(typeSerializedName)

        return when (typeSerializedName) {
            NoteContentDtoType.TEXT.serializedName -> {
                gson.fromJson<NoteContentDto.Text>(noteContentDtoJson)
            }
            NoteContentDtoType.IMAGE.serializedName -> {
                gson.fromJson<NoteContentDto.Image>(noteContentDtoJson)
            }
            NoteContentDtoType.AUDIO.serializedName -> {
                gson.fromJson<NoteContentDto.Audio>(noteContentDtoJson)
            }
            else -> null
        }
    }
}
