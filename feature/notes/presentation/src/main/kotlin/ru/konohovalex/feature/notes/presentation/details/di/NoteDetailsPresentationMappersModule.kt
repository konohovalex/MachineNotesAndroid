package ru.konohovalex.feature.notes.presentation.details.di

import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.notes.domain.model.NoteContentDomainModel
import ru.konohovalex.feature.notes.domain.model.NoteDomainModel
import ru.konohovalex.feature.notes.presentation.details.mapper.NoteContentDomainModelToNoteContentUiModelMapper
import ru.konohovalex.feature.notes.presentation.details.mapper.NoteDomainModelToNoteUiModelMapper
import ru.konohovalex.feature.notes.presentation.details.model.NoteContentUiModel
import ru.konohovalex.feature.notes.presentation.details.model.NoteUiModel

@Module
@DisableInstallInCheck
internal interface NoteDetailsPresentationMappersModule {
    @Binds
    fun bindNoteContentDomainModelToNoteContentUiModelMapper(
        mapper: NoteContentDomainModelToNoteContentUiModelMapper,
    ): Mapper<NoteContentDomainModel, NoteContentUiModel>

    @Binds
    fun bindNoteDomainModelToNoteUiModelMapper(
        mapper: NoteDomainModelToNoteUiModelMapper,
    ): Mapper<NoteDomainModel, NoteUiModel>
}
