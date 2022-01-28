package ru.konohovalex.feature.preferences.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.konohovalex.core.utils.model.OperationStatus
import ru.konohovalex.core.utils.extension.unwrap
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.preferences.data.model.ThemeMode
import ru.konohovalex.feature.preferences.data.repository.contract.PreferencesRepositoryContract
import ru.konohovalex.feature.preferences.domain.model.ThemeModeDomainModel
import javax.inject.Inject

class GetCurrentThemeModeUseCase
@Inject constructor(
    private val preferencesRepository: PreferencesRepositoryContract,
    private val themeModeToThemeModeDomainModelMapper: Mapper<ThemeMode, ThemeModeDomainModel>,
) {
    operator fun invoke(): Flow<OperationStatus.Plain<ThemeModeDomainModel>> = flow {
        try {
            emit(OperationStatus.Plain.Pending())

            emit(OperationStatus.Plain.Processing())

            val themeMode = preferencesRepository.getCurrentThemeMode().unwrap()
            val themeModeDomainModel = themeModeToThemeModeDomainModelMapper.invoke(themeMode)

            emit(OperationStatus.Plain.Completed(themeModeDomainModel))
        }
        catch (throwable: Throwable) {
            emit(OperationStatus.Plain.Error(throwable))
        }
    }
}
