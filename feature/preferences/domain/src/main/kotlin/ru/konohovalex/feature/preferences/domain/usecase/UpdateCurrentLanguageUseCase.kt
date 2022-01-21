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

class UpdateCurrentLanguageUseCase
@Inject constructor(
    private val preferencesRepository: PreferencesRepository,
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
            val updatedLanguage = preferencesRepository.updateCurrentLanguage(language).unwrap()
            val updatedLanguageDomainModel = languageToLanguageDomainModelMapper.invoke(updatedLanguage)

            emit(OperationStatus.WithInputData.Completed(languageDomainModel, updatedLanguageDomainModel))
        }
        catch (throwable: Throwable) {
            emit(OperationStatus.WithInputData.Error(languageDomainModel, throwable))
        }
    }
}
