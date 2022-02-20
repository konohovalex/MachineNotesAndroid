package ru.konohovalex.feature.notes.data.source.network

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import ru.konohovalex.feature.notes.data.model.remote.NoteContentDto
import java.io.IOException

internal class NoteContentDtoTypeAdapter : TypeAdapter<NoteContentDto>() {
    override fun write(writer: JsonWriter?, value: NoteContentDto?) {
        // TODO("complete")
    }

    override fun read(jsonReader: JsonReader?): NoteContentDto = jsonReader?.run {
        beginObject()

        val typeNameToken = peek()
        val noteContentDto = typeNameToken
            .takeIf { it == JsonToken.NAME }
            ?.let {
                when (val typeName = nextName()) {
                    NoteContentDto.Text.TYPE_SERIALIZED_NAME -> parseAsTextNoteContent(this)
                    NoteContentDto.Image.TYPE_SERIALIZED_NAME -> parseAsImageNoteContent(this)
                    NoteContentDto.Audio.TYPE_SERIALIZED_NAME -> parseAsAudioNoteContent(this)
                    else -> wrongTypeNameError(typeName)
                }
            } ?: wrongTypeNameTokenError(typeNameToken)

        endObject()

        noteContentDto
    } ?: jsonReaderIsNullError()

    private fun parseAsTextNoteContent(jsonReader: JsonReader): NoteContentDto.Text = with(jsonReader) {
        beginObject()

        var id: String? = null
        var content: String? = null
        while (hasNext()) {
            when (nextName()) {
                "id" -> id = nextString()
                "content" -> content = nextString()
            }
        }

        endObject()

        if (id != null && content != null) {
            NoteContentDto.Text(
                id = id,
                content = content,
            )
        }
        else throw IOException("\"id\" was [$id] and \"content\" was [$content]")
    }

    private fun parseAsImageNoteContent(jsonReader: JsonReader): NoteContentDto.Image = with(jsonReader) {
        beginObject()

        var id: String? = null
        var contentUrl: String? = null
        while (hasNext()) {
            when (nextName()) {
                "id" -> id = nextString()
                "contentUrl" -> contentUrl = nextString()
            }
        }

        endObject()

        if (id != null && contentUrl != null) {
            NoteContentDto.Image(
                id = id,
                contentUrl = contentUrl,
            )
        }
        else throw IOException("\"id\" was [$id] and \"contentUrl\" was [$contentUrl]")
    }

    private fun parseAsAudioNoteContent(jsonReader: JsonReader): NoteContentDto.Audio = with(jsonReader) {
        beginObject()

        var id: String? = null
        var contentUrl: String? = null
        while (hasNext()) {
            when (nextName()) {
                "id" -> id = nextString()
                "contentUrl" -> contentUrl = nextString()
            }
        }

        endObject()

        if (id != null && contentUrl != null) {
            NoteContentDto.Audio(
                id = id,
                contentUrl = contentUrl,
            )
        }
        else throw IOException("\"id\" was [$id] and \"contentUrl\" was [$contentUrl]")
    }

    private fun jsonReaderIsNullError(): Nothing =
        throw IllegalStateException("JsonReader was null")

    private fun wrongTypeNameTokenError(typeNameToken: JsonToken?): Nothing =
        throw IOException("Expecting \"${JsonToken.NAME}\", but was [$typeNameToken]")

    private fun wrongTypeNameError(typeName: String?): Nothing =
        throw IOException("Undefined object, expecting one of [\"text\", \"image\", \"audio\"], but was \"$typeName\"")
}
