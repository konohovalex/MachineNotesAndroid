package ru.konohovalex.feature.notes.data.source.network.api

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import ru.konohovalex.core.data.arch.source.network.api.Api
import ru.konohovalex.feature.notes.data.model.remote.NoteDto
import ru.konohovalex.feature.notes.data.model.remote.NoteUpdateParamsDto

internal interface NotesApi : Api {
    companion object {
        private const val NOTES_URL = "v1/notes"
    }

    @POST(NOTES_URL)
    suspend fun synchronizeNotes(@Body notes: List<NoteDto>): List<NoteDto>

    @POST("${NOTES_URL}/create")
    suspend fun createNote(): NoteDto

    @PUT("${NOTES_URL}/update/{noteId}")
    suspend fun updateNote(
        @Path("noteId") noteId: String,
        @Body note: NoteUpdateParamsDto,
    ): NoteDto

    @DELETE("${NOTES_URL}/delete/{noteId}")
    suspend fun deleteNote(@Path("noteId") noteId: String)

    @DELETE(NOTES_URL)
    suspend fun deleteAllNotes()
}
