package ru.konohovalex.feature.account.domain.auth.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.core.utils.model.OperationStatus
import ru.konohovalex.feature.account.data.auth.model.SignUpData
import ru.konohovalex.feature.account.data.profile.model.Profile
import ru.konohovalex.feature.account.data.repository.contract.AccountRepositoryContract
import ru.konohovalex.feature.account.domain.auth.model.SignUpDataDomainModel
import ru.konohovalex.feature.account.domain.profile.model.ProfileDomainModel
import ru.konohovalex.feature.notes.domain.usecase.SynchronizeNotesUseCase
import javax.inject.Inject

class SignUpUseCase
@Inject constructor(
    private val accountRepository: AccountRepositoryContract,
    private val synchronizeNotesUseCase: SynchronizeNotesUseCase,
    private val signUpDataDomainModelToSignUpDataMapper: Mapper<SignUpDataDomainModel, SignUpData>,
    private val profileToProfileDomainModelMapper: Mapper<Profile, ProfileDomainModel>,
) {
    operator fun invoke(
        signUpDataDomainModel: SignUpDataDomainModel,
    ): Flow<OperationStatus.WithInputData<SignUpDataDomainModel, ProfileDomainModel>> = flow {
        try {
            emit(OperationStatus.WithInputData.Pending(signUpDataDomainModel))

            emit(OperationStatus.WithInputData.Processing(signUpDataDomainModel))

            val signUpData = signUpDataDomainModelToSignUpDataMapper.invoke(signUpDataDomainModel)
            val profile = accountRepository.signUp(signUpData)
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
                            // TODO("log to Crashlytics and?")
                        }
                    }
                }

            emit(OperationStatus.WithInputData.Completed(signUpDataDomainModel, profileDomainModel))
        }
        catch (throwable: Throwable) {
            emit(OperationStatus.WithInputData.Error(signUpDataDomainModel, throwable))
        }
    }
}
