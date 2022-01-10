package ru.konohovalex.feature.notes.presentation.list.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.konohovalex.core.utils.Mapper
import ru.konohovalex.feature.notes.domain.model.NoteDomainModel
import ru.konohovalex.feature.notes.presentation.list.mapper.NoteDomainModelToNotePreviewUiModelMapper
import ru.konohovalex.feature.notes.presentation.list.model.NotePreviewUiModel

@Module
@InstallIn(SingletonComponent::class)
internal interface NoteListMappersModule {
    @Binds
    fun bindNoteDomainModelToNotePreviewUiModelMapper(
        mapper: NoteDomainModelToNotePreviewUiModelMapper,
    ): Mapper<NoteDomainModel, NotePreviewUiModel>
}
