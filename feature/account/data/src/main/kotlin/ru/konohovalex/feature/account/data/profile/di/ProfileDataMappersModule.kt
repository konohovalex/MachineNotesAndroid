package ru.konohovalex.feature.account.data.profile.di

import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.account.data.profile.mapper.ProfileDtoToProfileEntityMapper
import ru.konohovalex.feature.account.data.profile.mapper.ProfileEntityToProfileMapper
import ru.konohovalex.feature.account.data.profile.model.Profile
import ru.konohovalex.feature.account.data.profile.model.entity.ProfileEntity
import ru.konohovalex.feature.account.data.profile.model.remote.ProfileDto

@Module
@DisableInstallInCheck
internal interface ProfileDataMappersModule {
    @Binds
    fun bindProfileEntityToProfileMapper(
        mapper: ProfileEntityToProfileMapper,
    ): Mapper<ProfileEntity, Profile>


    @Binds
    fun bindProfileDtoToProfileEntityMapper(
        mapper: ProfileDtoToProfileEntityMapper,
    ): Mapper<ProfileDto, ProfileEntity>
}
