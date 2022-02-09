package ru.konohovalex.feature.notes.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.notes.data.model.Note
import ru.konohovalex.feature.notes.data.model.NoteContent
import ru.konohovalex.feature.notes.domain.mapper.NoteContentDomainModelToNoteContentMapper
import ru.konohovalex.feature.notes.domain.mapper.NoteContentToNoteContentDomainModelMapper
import ru.konohovalex.feature.notes.domain.mapper.NoteDomainModelToNoteMapper
import ru.konohovalex.feature.notes.domain.mapper.NoteToNoteDomainModelMapper
import ru.konohovalex.feature.notes.domain.model.NoteContentDomainModel
import ru.konohovalex.feature.notes.domain.model.NoteDomainModel

@Module
@DisableInstallInCheck
internal interface NotesDomainMappersModule {
    @Binds
    fun bindNoteContentDomainModelToNoteContentMapper(
        mapper: NoteContentDomainModelToNoteContentMapper,
    ): Mapper<NoteContentDomainModel, NoteContent>

    @Binds
    fun bindNoteContentToNoteContentDomainModelMapper(
        mapper: NoteContentToNoteContentDomainModelMapper,
    ): Mapper<NoteContent, NoteContentDomainModel>


    @Binds
    fun bindNoteDomainModelToNoteMapper(
        mapper: NoteDomainModelToNoteMapper,
    ): Mapper<NoteDomainModel, Note>

    @Binds
    fun bindNoteToNoteDomainModelMapper(
        mapper: NoteToNoteDomainModelMapper,
    ): Mapper<Note, NoteDomainModel>
}
