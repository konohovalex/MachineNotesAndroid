package ru.konohovalex.feature.account.domain.profile.mapper

import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.account.data.profile.model.Profile
import ru.konohovalex.feature.account.domain.profile.model.ProfileDomainModel
import javax.inject.Inject

internal class ProfileToProfileDomainModelMapper
@Inject constructor() : Mapper<Profile, ProfileDomainModel> {
    override fun invoke(input: Profile) = with(input) {
        ProfileDomainModel(
            userName = userName,
        )
    }
}
