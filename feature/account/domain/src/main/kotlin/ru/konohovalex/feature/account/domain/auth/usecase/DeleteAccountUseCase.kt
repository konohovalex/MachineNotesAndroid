package ru.konohovalex.feature.account.domain.auth.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.core.utils.model.OperationStatus
import ru.konohovalex.feature.account.data.profile.model.Profile
import ru.konohovalex.feature.account.data.repository.contract.AccountRepositoryContract
import ru.konohovalex.feature.account.domain.profile.model.ProfileDomainModel
import ru.konohovalex.feature.notes.domain.model.NotesDeletionMode
import ru.konohovalex.feature.notes.domain.usecase.DeleteAllNotesUseCase
import javax.inject.Inject

class DeleteAccountUseCase
@Inject constructor(
    private val accountRepository: AccountRepositoryContract,
    private val deleteAllNotesUseCase: DeleteAllNotesUseCase,
    private val profileToProfileDomainModelMapper: Mapper<Profile, ProfileDomainModel>,
) {
    operator fun invoke(): Flow<OperationStatus.Plain<ProfileDomainModel>> = flow {
        try {
            emit(OperationStatus.Plain.Pending())

            emit(OperationStatus.Plain.Processing())

            val profile = accountRepository.deleteAccount()
            val profileDomainModel = profileToProfileDomainModelMapper.invoke(profile)

            deleteAllNotesUseCase.invoke(NotesDeletionMode.ONLY_LOCALLY)
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

            emit(OperationStatus.Plain.Completed(profileDomainModel))
        }
        catch (throwable: Throwable) {
            emit(OperationStatus.Plain.Error(throwable))
        }
    }
}
