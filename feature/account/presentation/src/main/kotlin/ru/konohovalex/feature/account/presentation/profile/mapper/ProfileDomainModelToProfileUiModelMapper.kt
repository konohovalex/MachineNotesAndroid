package ru.konohovalex.feature.account.presentation.profile.mapper

import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.account.domain.profile.model.ProfileDomainModel
import ru.konohovalex.feature.account.presentation.profile.extension.createDummyNotesStatistics
import ru.konohovalex.feature.account.presentation.profile.model.ProfileUiModel
import javax.inject.Inject

internal class ProfileDomainModelToProfileUiModelMapper
@Inject constructor() : Mapper<ProfileDomainModel, ProfileUiModel> {
    override fun invoke(input: ProfileDomainModel) = with(input) {
        ProfileUiModel(
            id = id,
            name = name,
            notesStatistics = createDummyNotesStatistics(),
        )
    }
}
