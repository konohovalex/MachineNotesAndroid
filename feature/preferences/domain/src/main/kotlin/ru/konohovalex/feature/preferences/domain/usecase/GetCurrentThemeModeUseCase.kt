package ru.konohovalex.feature.preferences.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.konohovalex.core.data.model.OperationStatus
import ru.konohovalex.core.data.utils.unwrap
import ru.konohovalex.core.utils.Mapper
import ru.konohovalex.feature.preferences.data.model.ThemeMode
import ru.konohovalex.feature.preferences.data.repository.contract.PreferencesRepository
import ru.konohovalex.feature.preferences.domain.model.ThemeModeDomainModel
import javax.inject.Inject

class GetCurrentThemeModeUseCase
@Inject constructor(
    private val preferencesRepository: PreferencesRepository,
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
