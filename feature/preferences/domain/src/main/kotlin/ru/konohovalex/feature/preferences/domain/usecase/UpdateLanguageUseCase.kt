package ru.konohovalex.feature.preferences.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.core.utils.model.OperationStatus
import ru.konohovalex.feature.preferences.data.model.Language
import ru.konohovalex.feature.preferences.data.repository.contract.PreferencesRepositoryContract
import ru.konohovalex.feature.preferences.domain.model.LanguageDomainModel
import javax.inject.Inject

class UpdateLanguageUseCase
@Inject constructor(
    private val preferencesRepository: PreferencesRepositoryContract,
    private val languageDomainModelToLanguageMapper: Mapper<LanguageDomainModel, Language>,
    private val languageToLanguageDomainModelMapper: Mapper<Language, LanguageDomainModel>,
) {
    operator fun invoke(
        languageDomainModel: LanguageDomainModel,
    ): Flow<OperationStatus.WithInputData<LanguageDomainModel, LanguageDomainModel>> = flow {
        try {
            emit(OperationStatus.WithInputData.Pending(languageDomainModel))

            emit(OperationStatus.WithInputData.Processing(languageDomainModel))

            val language = languageDomainModelToLanguageMapper.invoke(languageDomainModel)
            val updatedLanguage = preferencesRepository.updateLanguage(language)
            val updatedLanguageDomainModel = languageToLanguageDomainModelMapper.invoke(updatedLanguage)

            emit(OperationStatus.WithInputData.Completed(languageDomainModel, updatedLanguageDomainModel))
        }
        catch (throwable: Throwable) {
            emit(OperationStatus.WithInputData.Error(languageDomainModel, throwable))
        }
    }
}
