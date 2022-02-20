package ru.konohovalex.feature.account.data.profile.mapper

import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.account.data.profile.model.entity.ProfileEntity
import ru.konohovalex.feature.account.data.profile.model.remote.ProfileDto
import javax.inject.Inject

internal class ProfileDtoToProfileEntityMapper
@Inject constructor() : Mapper<ProfileDto, ProfileEntity> {
    override fun invoke(input: ProfileDto) = with(input) {
        ProfileEntity(
            userName = userName,
        )
    }
}
