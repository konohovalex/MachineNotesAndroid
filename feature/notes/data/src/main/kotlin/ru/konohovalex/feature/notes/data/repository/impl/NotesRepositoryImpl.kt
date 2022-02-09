package ru.konohovalex.feature.notes.data.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.konohovalex.core.utils.extension.withIo
import ru.konohovalex.core.utils.model.DateTime
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.notes.data.model.Note
import ru.konohovalex.feature.notes.data.model.entity.NoteWithContentEntity
import ru.konohovalex.feature.notes.data.model.remote.NoteDto
import ru.konohovalex.feature.notes.data.model.remote.NoteUpdateParamsDto
import ru.konohovalex.feature.notes.data.repository.contract.NotesRepositoryContract
import ru.konohovalex.feature.notes.data.source.api.NotesApi
import ru.konohovalex.feature.notes.data.source.storage.contract.NotesStorageContract
import javax.inject.Inject
import kotlin.random.Random

internal class NotesRepositoryImpl
@Inject constructor(
    private val notesApi: NotesApi,
    private val notesStorage: NotesStorageContract,
    private val noteDtoToNoteMapper: Mapper<NoteDto, Note>,
    private val noteToNoteDtoMapper: Mapper<Note, NoteDto>,
    private val noteWithContentEntityToNoteMapper: Mapper<NoteWithContentEntity, Note>,
    private val noteToNoteWithContentEntityMapper: Mapper<Note, NoteWithContentEntity>,
    private val noteToNoteUpdateParamsDtoMapper: Mapper<Note, NoteUpdateParamsDto>,
) : NotesRepositoryContract {
    override suspend fun synchronizeNotes() {
//        val localNotes = notesStorage.observeNotes()
//            .value
//            .map(noteWithContentEntityToNoteMapper)
//        val localNotesDtoList = localNotes.map(noteToNoteDtoMapper)
//        val synchronizedNotes = notesApi.synchronizeNotes(localNotesDtoList).map(noteDtoToNoteMapper)
//        notesStorage.insertNotes(synchronizedNotes.map(noteToNoteWithContentEntityMapper))
    }

    override suspend fun observeNotes(): Flow<List<Note>> = withIo {
        notesStorage.observeNotes()
            .map { it.map(noteWithContentEntityToNoteMapper) }
    }

    override suspend fun createNote(): Note = withIo {
//        val noteDto = notesApi.createNote()
//        val note = noteDtoToNoteMapper.invoke(noteDto)
        val note = dummyNote(notesStorage.observeNotes().value.size)
        val noteWithContentEntity = noteToNoteWithContentEntityMapper.invoke(note)
        notesStorage.insertNotes(listOf(noteWithContentEntity))
        note
    }

    override suspend fun getNoteDetails(noteId: String): Note = withIo {
        notesStorage.getNote(noteId).let(noteWithContentEntityToNoteMapper::invoke)
    }

    override suspend fun updateNote(note: Note): Note = withIo {
//        val noteUpdateParamsDto = noteToNoteUpdateParamsDtoMapper.invoke(note)
//        val noteDto = notesApi.updateNote(note.id, noteUpdateParamsDto)
//        val updatedNote = noteDtoToNoteMapper.invoke(noteDto)
//        val noteWithContentEntity = noteToNoteWithContentEntityMapper.invoke(updatedNote)
//        notesStorage.updateNotes(listOf(noteWithContentEntity))
//        updatedNote
        val noteWithContentEntity = noteToNoteWithContentEntityMapper.invoke(note)
        notesStorage.updateNotes(listOf(noteWithContentEntity))
        note
    }

    override suspend fun deleteNote(noteId: String) = withIo {
//        notesApi.deleteNote(note.id)
        notesStorage.deleteNotes(listOf(noteId))
    }

    override suspend fun deleteAllNotes(onlyLocally: Boolean) = withIo {
//        if (!onlyLocally) notesApi.deleteAllNotes()
        notesStorage.deleteAllNotes()
    }

    private fun dummyNote(notesSize: Int) = DateTime().let {
        Note("${notesSize}_${Random.nextInt(100000)}", it, it, listOf())
    }
}
