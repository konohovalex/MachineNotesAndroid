package ru.konohovalex.feature.account.presentation.profile.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.account.domain.profile.model.ProfileDomainModel
import ru.konohovalex.feature.account.presentation.profile.mapper.ProfileDomainModelToProfileUiModelMapper
import ru.konohovalex.feature.account.presentation.profile.model.ProfileUiModel

@Module
@InstallIn(SingletonComponent::class)
internal interface ProfilePresentationMappersModule {
    @Binds
    fun bindProfileDomainModelToProfileUiModelMapper(
        mapper: ProfileDomainModelToProfileUiModelMapper,
    ): Mapper<ProfileDomainModel, ProfileUiModel>
}
