package ru.konohovalex.feature.preferences.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.core.utils.model.OperationStatus
import ru.konohovalex.feature.preferences.data.model.Preferences
import ru.konohovalex.feature.preferences.data.repository.contract.PreferencesRepositoryContract
import ru.konohovalex.feature.preferences.domain.model.PreferencesDomainModel
import javax.inject.Inject

class ObservePreferencesUseCase
@Inject constructor(
    private val preferencesRepository: PreferencesRepositoryContract,
    private val preferencesToPreferencesDomainModelMapper: Mapper<Preferences, PreferencesDomainModel>,
) {
    suspend operator fun invoke(): Flow<OperationStatus.Plain<PreferencesDomainModel>> =
        preferencesRepository.observePreferences()
            .map<Preferences, OperationStatus.Plain<PreferencesDomainModel>> {
                val preferencesDomainModel = preferencesToPreferencesDomainModelMapper.invoke(it)
                OperationStatus.Plain.Completed(preferencesDomainModel)
            }
            .catch { exception -> emit(OperationStatus.Plain.Error(exception)) }
}
