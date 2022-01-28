package ru.konohovalex.feature.account.domain.profile.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.account.data.profile.model.Profile
import ru.konohovalex.feature.account.domain.profile.mapper.ProfileToProfileDomainModelMapper
import ru.konohovalex.feature.account.domain.profile.model.ProfileDomainModel

@Module
@InstallIn(SingletonComponent::class)
internal interface ProfileDomainMappersModule {
    @Binds
    fun bindProfileToProfileDomainModelMapper(
        mapper: ProfileToProfileDomainModelMapper,
    ): Mapper<Profile, ProfileDomainModel>
}
