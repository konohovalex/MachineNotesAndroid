package ru.konohovalex.feature.account.data.profile.mapper

import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.account.data.profile.model.Profile
import ru.konohovalex.feature.account.data.profile.model.entity.ProfileEntity
import javax.inject.Inject

internal class ProfileEntityToProfileMapper
@Inject constructor() : Mapper<ProfileEntity, Profile> {
    override fun invoke(input: ProfileEntity) = with(input) {
        Profile(
            userName = userName,
        )
    }
}
