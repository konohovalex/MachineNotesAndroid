package ru.konohovalex.feature.notes.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.notes.data.mapper.NoteContentDtoToNoteContentMapper
import ru.konohovalex.feature.notes.data.mapper.NoteContentEntityToNoteContentMapper
import ru.konohovalex.feature.notes.data.mapper.NoteContentToNoteContentDtoMapper
import ru.konohovalex.feature.notes.data.mapper.NoteContentToNoteContentEntityMapper
import ru.konohovalex.feature.notes.data.mapper.NoteDtoToNoteMapper
import ru.konohovalex.feature.notes.data.mapper.NoteEntityToNoteMapper
import ru.konohovalex.feature.notes.data.mapper.NoteToNoteDtoMapper
import ru.konohovalex.feature.notes.data.mapper.NoteToNoteEntityMapper
import ru.konohovalex.feature.notes.data.mapper.NoteUpdateParamsToNoteUpdateParamsDtoMapper
import ru.konohovalex.feature.notes.data.model.Note
import ru.konohovalex.feature.notes.data.model.NoteContent
import ru.konohovalex.feature.notes.data.model.NoteUpdateParams
import ru.konohovalex.feature.notes.data.model.entity.NoteContentEntity
import ru.konohovalex.feature.notes.data.model.entity.NoteEntity
import ru.konohovalex.feature.notes.data.model.remote.NoteContentDto
import ru.konohovalex.feature.notes.data.model.remote.NoteDto
import ru.konohovalex.feature.notes.data.model.remote.NoteUpdateParamsDto

@Module
@DisableInstallInCheck
internal interface NotesDataMappersModule {
    @Binds
    fun bindNoteContentDtoToNoteContentMapper(
        mapper: NoteContentDtoToNoteContentMapper,
    ): Mapper<NoteContentDto, NoteContent>

    @Binds
    fun bindNoteDtoToNoteMapper(
        mapper: NoteDtoToNoteMapper,
    ): Mapper<NoteDto, Note>


    @Binds
    fun bindNoteContentToNoteContentDtoMapper(
        mapper: NoteContentToNoteContentDtoMapper,
    ): Mapper<NoteContent, NoteContentDto>

    @Binds
    fun bindNoteToNoteDtoMapper(
        mapper: NoteToNoteDtoMapper,
    ): Mapper<Note, NoteDto>


    @Binds
    fun bindNoteContentEntityToNoteContentMapper(
        mapper: NoteContentEntityToNoteContentMapper,
    ): Mapper<NoteContentEntity, NoteContent>

    @Binds
    fun bindNoteEntityToNoteMapper(
        mapper: NoteEntityToNoteMapper,
    ): Mapper<NoteEntity, Note>


    @Binds
    fun bindNoteContentToNoteContentEntityMapper(
        mapper: NoteContentToNoteContentEntityMapper,
    ): Mapper<NoteContent, NoteContentEntity>

    @Binds
    fun bindNoteToNoteEntityMapper(
        mapper: NoteToNoteEntityMapper,
    ): Mapper<Note, NoteEntity>


    @Binds
    fun bindNoteUpdateParamsToNoteUpdateParamsDtoMapper(
        mapper: NoteUpdateParamsToNoteUpdateParamsDtoMapper,
    ): Mapper<NoteUpdateParams, NoteUpdateParamsDto>
}
