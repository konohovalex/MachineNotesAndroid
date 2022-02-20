package ru.konohovalex.feature.account.domain.auth.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.core.utils.model.OperationStatus
import ru.konohovalex.feature.account.data.auth.model.Credentials
import ru.konohovalex.feature.account.data.auth.model.Password
import ru.konohovalex.feature.account.data.auth.model.UserName
import ru.konohovalex.feature.account.data.profile.model.Profile
import ru.konohovalex.feature.account.data.repository.contract.AccountRepositoryContract
import ru.konohovalex.feature.account.domain.auth.model.CredentialsDomainModel
import ru.konohovalex.feature.account.domain.profile.model.ProfileDomainModel
import ru.konohovalex.feature.notes.domain.usecase.SynchronizeNotesUseCase
import javax.inject.Inject

class SingInUseCase
@Inject constructor(
    private val accountRepository: AccountRepositoryContract,
    private val synchronizeNotesUseCase: SynchronizeNotesUseCase,
    private val profileToProfileDomainModelMapper: Mapper<Profile, ProfileDomainModel>,
) {
    operator fun invoke(
        credentialsDomainModel: CredentialsDomainModel,
    ): Flow<OperationStatus.WithInputData<CredentialsDomainModel, ProfileDomainModel>> = flow {
        try {
            emit(OperationStatus.WithInputData.Pending(credentialsDomainModel))

            emit(OperationStatus.WithInputData.Processing(credentialsDomainModel))

            val credentials = Credentials(
                userName = UserName(credentialsDomainModel.userNameDomainModel.value),
                password = Password(credentialsDomainModel.passwordDomainModel.value),
            )
            val profile = accountRepository.signIn(credentials)
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

            emit(OperationStatus.WithInputData.Completed(credentialsDomainModel, profileDomainModel))
        }
        catch (throwable: Throwable) {
            emit(OperationStatus.WithInputData.Error(credentialsDomainModel, throwable))
        }
    }
}
