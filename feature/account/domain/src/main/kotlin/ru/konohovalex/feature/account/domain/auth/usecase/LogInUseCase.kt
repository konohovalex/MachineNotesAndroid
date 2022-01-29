package ru.konohovalex.feature.account.domain.auth.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.core.utils.model.OperationStatus
import ru.konohovalex.feature.account.data.auth.model.AuthData
import ru.konohovalex.feature.account.data.profile.model.Profile
import ru.konohovalex.feature.account.data.profile.repository.contract.ProfileRepositoryContract
import ru.konohovalex.feature.account.domain.auth.model.AuthDataDomainModel
import ru.konohovalex.feature.account.domain.profile.model.ProfileDomainModel
import javax.inject.Inject

class LogInUseCase
@Inject constructor(
    private val profileRepository: ProfileRepositoryContract,
    private val authDataDomainModelToAuthDataMapper: Mapper<AuthDataDomainModel, AuthData>,
    private val profileToProfileDomainModelMapper: Mapper<Profile, ProfileDomainModel>,
) {
    operator fun invoke(
        authDataDomainModel: AuthDataDomainModel?,
    ): Flow<OperationStatus.WithInputData<AuthDataDomainModel?, ProfileDomainModel>> = flow {
        try {
            emit(OperationStatus.WithInputData.Pending(authDataDomainModel))

            emit(OperationStatus.WithInputData.Processing(authDataDomainModel))

            val authData = authDataDomainModel?.let(authDataDomainModelToAuthDataMapper::invoke)
            val profile = profileRepository.logIn(authData)
            val profileDomainModel = profileToProfileDomainModelMapper.invoke(profile)

            emit(OperationStatus.WithInputData.Completed(authDataDomainModel, profileDomainModel))
        }
        catch (throwable: Throwable) {
            emit(OperationStatus.WithInputData.Error(authDataDomainModel, throwable))
        }
    }
}
