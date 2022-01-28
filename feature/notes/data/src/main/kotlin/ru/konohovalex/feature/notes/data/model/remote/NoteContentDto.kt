package ru.konohovalex.feature.notes.data.model.remote

import com.google.gson.annotations.SerializedName

internal sealed class NoteContentDto {
    abstract val id: String

    data class Text(
        @SerializedName(ID_SERIALIZED_NAME)
        override val id: String,
        @SerializedName(CONTENT_SERIALIZED_NAME)
        val content: String,
    ) : NoteContentDto() {
        companion object {
            const val TYPE_SERIALIZED_NAME = "text"
            const val ID_SERIALIZED_NAME = "id"
            const val CONTENT_SERIALIZED_NAME = "content"
        }
    }

    data class Image(
        @SerializedName(ID_SERIALIZED_NAME)
        override val id: String,
        @SerializedName(Audio.CONTENT_URL_SERIALIZED_NAME)
        val contentUrl: String,
    ) : NoteContentDto() {
        companion object {
            const val TYPE_SERIALIZED_NAME = "image"
            const val ID_SERIALIZED_NAME = "id"
            const val CONTENT_URL_SERIALIZED_NAME = "contentUrl"
        }
    }

    data class Audio(
        @SerializedName(ID_SERIALIZED_NAME)
        override val id: String,
        @SerializedName(CONTENT_URL_SERIALIZED_NAME)
        val contentUrl: String,
    ) : NoteContentDto() {
        companion object {
            const val TYPE_SERIALIZED_NAME = "audio"
            const val ID_SERIALIZED_NAME = "id"
            const val CONTENT_URL_SERIALIZED_NAME = "contentUrl"
        }
    }
}
