package ru.konohovalex.feature.notes.data.source.api

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import ru.konohovalex.core.data.arch.source.api.Api
import ru.konohovalex.feature.notes.data.model.remote.NoteDto
import ru.konohovalex.feature.notes.data.model.remote.NoteUpdateParamsDto

internal interface NotesApi : Api {
    companion object {
        private const val NOTES_URL = "v1/notes"
    }

    @POST("${NOTES_URL}/create")
    suspend fun createNote(): NoteDto

    @GET("${NOTES_URL}/details/{noteId}")
    suspend fun getNoteDetails(@Path("noteId") noteId: String): NoteDto

    @PUT("${NOTES_URL}/update/{noteId}")
    suspend fun updateNote(
        @Path("noteId") noteId: String,
        @Body note: NoteUpdateParamsDto,
    ): NoteDto

    @DELETE("${NOTES_URL}/delete/{noteId}")
    suspend fun deleteNote(@Path("noteId") noteId: String)

    @GET(NOTES_URL)
    suspend fun getNotes(
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int,
    ): List<NoteDto>

    @POST(NOTES_URL)
    suspend fun synchronizeNotes(@Body notes: List<NoteDto>): List<NoteDto>

    @DELETE("${NOTES_URL}/deleteAll")
    suspend fun deleteAllNotes()
}
