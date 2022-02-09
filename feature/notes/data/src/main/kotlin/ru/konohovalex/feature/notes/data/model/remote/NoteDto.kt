package ru.konohovalex.feature.notes.data.model.remote

import com.google.gson.annotations.SerializedName

internal data class NoteDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("dateTimeCreated")
    val dateTimeCreated: String,
    @SerializedName("dateTimeLastEdited")
    val dateTimeLastEdited: String,
    @SerializedName("noteContent")
    val noteContent: List<NoteContentDto>,
)
