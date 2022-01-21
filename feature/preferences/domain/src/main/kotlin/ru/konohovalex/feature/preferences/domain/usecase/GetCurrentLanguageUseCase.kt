package ru.konohovalex.feature.preferences.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.konohovalex.core.data.model.OperationStatus
import ru.konohovalex.core.data.utils.unwrap
import ru.konohovalex.core.utils.Mapper
import ru.konohovalex.feature.preferences.data.model.Language
import ru.konohovalex.feature.preferences.data.repository.contract.PreferencesRepository
import ru.konohovalex.feature.preferences.domain.model.LanguageDomainModel
import javax.inject.Inject

class GetCurrentLanguageUseCase
@Inject constructor(
    private val preferencesRepository: PreferencesRepository,
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
