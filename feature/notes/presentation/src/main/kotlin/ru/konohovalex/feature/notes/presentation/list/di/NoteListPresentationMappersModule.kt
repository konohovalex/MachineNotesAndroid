package ru.konohovalex.feature.notes.presentation.list.di

import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.notes.domain.model.NoteDomainModel
import ru.konohovalex.feature.notes.presentation.list.mapper.NoteDomainModelToNotePreviewUiModelMapper
import ru.konohovalex.feature.notes.presentation.list.model.NotePreviewUiModel

@Module
@DisableInstallInCheck
internal interface NoteListPresentationMappersModule {
    @Binds
    fun bindNoteDomainModelToNotePreviewUiModelMapper(
        mapper: NoteDomainModelToNotePreviewUiModelMapper,
    ): Mapper<NoteDomainModel, NotePreviewUiModel>
}
