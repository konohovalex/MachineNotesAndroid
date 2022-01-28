package ru.konohovalex.feature.preferences.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.konohovalex.core.utils.model.OperationStatus
import ru.konohovalex.core.utils.extension.unwrap
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.preferences.data.model.Language
import ru.konohovalex.feature.preferences.data.repository.contract.PreferencesRepositoryContract
import ru.konohovalex.feature.preferences.domain.model.LanguageDomainModel
import javax.inject.Inject

class GetCurrentLanguageUseCase
@Inject constructor(
    private val preferencesRepository: PreferencesRepositoryContract,
    private val languageToLanguageDomainModelMapper: Mapper<Language, LanguageDomainModel>,
) {
    operator fun invoke(): Flow<OperationStatus.Plain<LanguageDomainModel>> = flow {
        try {
            emit(OperationStatus.Plain.Pending())

            emit(OperationStatus.Plain.Processing())

            val language = preferencesRepository.getCurrentLanguage().unwrap()
            val languageDomainModel = languageToLanguageDomainModelMapper.invoke(language)

            emit(OperationStatus.Plain.Completed(languageDomainModel))
        }
        catch (throwable: Throwable) {
            emit(OperationStatus.Plain.Error(throwable))
        }
    }
}
