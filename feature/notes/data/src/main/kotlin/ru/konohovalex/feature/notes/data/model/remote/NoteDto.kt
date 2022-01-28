package ru.konohovalex.feature.notes.data.model.remote

import com.google.gson.annotations.SerializedName
import ru.konohovalex.core.utils.model.DateTime

internal data class NoteDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("dateTimeCreated")
    val dateTimeCreated: DateTime,
    @SerializedName("dateTimeLastEdited")
    val dateTimeLastEdited: DateTime,
    @SerializedName("noteContent")
    val noteContent: List<NoteContentDto>,
)
