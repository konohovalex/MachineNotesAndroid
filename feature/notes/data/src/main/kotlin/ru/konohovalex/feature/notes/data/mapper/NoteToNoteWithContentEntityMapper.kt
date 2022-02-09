package ru.konohovalex.feature.notes.data.mapper

import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.core.utils.model.ParameterizedMapper
import ru.konohovalex.feature.notes.data.model.Note
import ru.konohovalex.feature.notes.data.model.NoteContent
import ru.konohovalex.feature.notes.data.model.entity.NoteContentEntity
import ru.konohovalex.feature.notes.data.model.entity.NoteEntity
import ru.konohovalex.feature.notes.data.model.entity.NoteWithContentEntity
import javax.inject.Inject

internal class NoteToNoteWithContentEntityMapper
@Inject constructor(
    private val noteToNoteEntityMapper: Mapper<Note, NoteEntity>,
    private val noteContentToNoteContentEntityMapper: ParameterizedMapper<NoteContent, NoteEntity, NoteContentEntity>,
) : Mapper<Note, NoteWithContentEntity> {
    override fun invoke(input: Note) = noteToNoteEntityMapper.invoke(input).let {
        NoteWithContentEntity(
            note = it,
            noteContent = input.noteContent.map(it.let(noteContentToNoteContentEntityMapper::invoke)),
        )
    }
}
