package ru.konohovalex.feature.account.domain.auth.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.core.utils.model.OperationStatus
import ru.konohovalex.feature.account.data.auth.model.AuthData
import ru.konohovalex.feature.account.data.profile.model.Profile
import ru.konohovalex.feature.account.data.repository.contract.AccountRepositoryContract
import ru.konohovalex.feature.account.domain.auth.model.AuthDataDomainModel
import ru.konohovalex.feature.account.domain.profile.model.ProfileDomainModel
import ru.konohovalex.feature.notes.domain.usecase.SynchronizeNotesUseCase
import javax.inject.Inject

class LogInUseCase
@Inject constructor(
    private val accountRepository: AccountRepositoryContract,
    private val synchronizeNotesUseCase: SynchronizeNotesUseCase,
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
            val profile = accountRepository.logIn(authData)
            val profileDomainModel = profileToProfileDomainModelMapper.invoke(profile)

            synchronizeNotesUseCase.invoke()
                .collect {
                    when (it) {
                        is OperationStatus.Plain.Pending,
                        is OperationStatus.Plain.Processing,
                        is OperationStatus.Plain.Completed,
                        -> {
                        }
                        is OperationStatus.Plain.Error -> {
                            // tbd log to Crashlytics and?
                        }
                    }
                }

            emit(OperationStatus.WithInputData.Completed(authDataDomainModel, profileDomainModel))
        }
        catch (throwable: Throwable) {
            emit(OperationStatus.WithInputData.Error(authDataDomainModel, throwable))
        }
    }
}
