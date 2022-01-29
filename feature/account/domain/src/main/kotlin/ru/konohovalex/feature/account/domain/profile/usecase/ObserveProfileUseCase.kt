package ru.konohovalex.feature.account.domain.profile.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.core.utils.model.OperationStatus
import ru.konohovalex.feature.account.data.profile.model.Profile
import ru.konohovalex.feature.account.data.profile.repository.contract.ProfileRepositoryContract
import ru.konohovalex.feature.account.domain.profile.model.ProfileDomainModel
import javax.inject.Inject

class ObserveProfileUseCase
@Inject constructor(
    private val profileRepository: ProfileRepositoryContract,
    private val profileToProfileDomainModelMapper: Mapper<Profile, ProfileDomainModel>,
) {
    suspend operator fun invoke(): Flow<OperationStatus.Plain<ProfileDomainModel?>> =
        profileRepository.observeProfile()
            .map<Profile?, OperationStatus.Plain<ProfileDomainModel?>> {
                val profileDomainModel = it?.let(profileToProfileDomainModelMapper::invoke)
                OperationStatus.Plain.Completed(profileDomainModel)
            }
            .catch { exception -> emit(OperationStatus.Plain.Error(exception)) }
}
