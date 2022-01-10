package ru.konohovalex.feature.notes.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.konohovalex.core.utils.Mapper
import ru.konohovalex.feature.notes.data.model.Note
import ru.konohovalex.feature.notes.data.model.NoteContent
import ru.konohovalex.feature.notes.data.model.NoteUpdateParams
import ru.konohovalex.feature.notes.domain.mapper.NoteContentDomainModelToNoteContentMapper
import ru.konohovalex.feature.notes.domain.mapper.NoteContentToNoteContentDomainModelMapper
import ru.konohovalex.feature.notes.domain.mapper.NoteDomainModelToNoteMapper
import ru.konohovalex.feature.notes.domain.mapper.NoteToNoteDomainModelMapper
import ru.konohovalex.feature.notes.domain.mapper.NoteUpdateParamsDomainModelToNoteUpdateParamsMapper
import ru.konohovalex.feature.notes.domain.model.NoteContentDomainModel
import ru.konohovalex.feature.notes.domain.model.NoteDomainModel
import ru.konohovalex.feature.notes.domain.model.NoteUpdateParamsDomainModel

@Module
@InstallIn(SingletonComponent::class)
internal interface NotesDomainMappersModule {
    @Binds
    fun bindNoteContentToNoteContentDomainModelMapper(
        mapper: NoteContentToNoteContentDomainModelMapper,
    ): Mapper<NoteContent, NoteContentDomainModel>

    @Binds
    fun bindNoteToNoteDomainModelMapper(
        mapper: NoteToNoteDomainModelMapper,
    ): Mapper<Note, NoteDomainModel>


    @Binds
    fun bindNoteContentDomainModelToNoteContentMapper(
        mapper: NoteContentDomainModelToNoteContentMapper,
    ): Mapper<NoteContentDomainModel, NoteContent>

    @Binds
    fun bindNoteDomainModelToNoteMapper(
        mapper: NoteDomainModelToNoteMapper,
    ): Mapper<NoteDomainModel, Note>


    @Binds
    fun bindNoteUpdateParamsDomainModelToNoteUpdateParamsMapper(
        mapper: NoteUpdateParamsDomainModelToNoteUpdateParamsMapper,
    ): Mapper<NoteUpdateParamsDomainModel, NoteUpdateParams>
}
