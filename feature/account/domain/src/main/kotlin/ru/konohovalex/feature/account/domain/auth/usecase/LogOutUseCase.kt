package ru.konohovalex.feature.account.domain.auth.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.core.utils.model.OperationStatus
import ru.konohovalex.feature.account.data.profile.model.Profile
import ru.konohovalex.feature.account.data.profile.repository.contract.ProfileRepositoryContract
import ru.konohovalex.feature.account.domain.profile.model.ProfileDomainModel
import javax.inject.Inject

class LogOutUseCase
@Inject constructor(
    private val profileRepository: ProfileRepositoryContract,
    private val profileToProfileDomainModelMapper: Mapper<Profile, ProfileDomainModel>,
) {
    operator fun invoke(): Flow<OperationStatus.Plain<ProfileDomainModel>> = flow {
        try {
            emit(OperationStatus.Plain.Pending())

            emit(OperationStatus.Plain.Processing())

            val profile = profileRepository.deleteAccount()
            val profileDomainModel = profileToProfileDomainModelMapper.invoke(profile)

            emit(OperationStatus.Plain.Completed(profileDomainModel))
        }
        catch (throwable: Throwable) {
            emit(OperationStatus.Plain.Error(throwable))
        }
    }
}
