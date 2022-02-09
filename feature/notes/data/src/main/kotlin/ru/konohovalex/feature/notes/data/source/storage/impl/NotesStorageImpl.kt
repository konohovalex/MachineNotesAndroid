package ru.konohovalex.feature.notes.data.source.storage.impl

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import ru.konohovalex.feature.notes.data.model.entity.NoteWithContentEntity
import ru.konohovalex.feature.notes.data.source.storage.contract.NotesStorageContract
import ru.konohovalex.feature.notes.data.source.storage.database.NotesDao
import javax.inject.Inject

internal class NotesStorageImpl
@Inject constructor(
    private val coroutineScope: CoroutineScope,
    private val notesDao: NotesDao,
) : NotesStorageContract {
    private var notesStateFlow: StateFlow<List<NoteWithContentEntity>>? = null

    override suspend fun observeNotes(): StateFlow<List<NoteWithContentEntity>> =
        getNotesStateFlow()

    override suspend fun getNote(noteId: String): NoteWithContentEntity =
        notesDao.getNote(noteId)

    override suspend fun insertNotes(notes: List<NoteWithContentEntity>) =
        notesDao.insertNotes(notes)

    override suspend fun updateNotes(notes: List<NoteWithContentEntity>) =
        notesDao.updateNotes(notes)

    override suspend fun deleteNotes(noteIds: List<String>) =
        notesDao.deleteNotes(noteIds)

    override suspend fun deleteAllNotes() =
        notesDao.deleteAllNotes()

    private suspend fun getNotesStateFlow(): StateFlow<List<NoteWithContentEntity>> =
        notesStateFlow ?: initNotesStateFlow()

    private suspend fun initNotesStateFlow(): StateFlow<List<NoteWithContentEntity>> =
        notesDao.observeNotes()
            .stateIn(coroutineScope)
            .also { notesStateFlow = it }
}
