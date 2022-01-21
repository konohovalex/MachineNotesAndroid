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

class UpdateCurrentThemeModeUseCase
@Inject constructor(
    private val preferencesRepository: PreferencesRepository,
    private val themeModeDomainModelToThemeModeMapper: Mapper<ThemeModeDomainModel, ThemeMode>,
    private val themeModeToThemeModeDomainModelMapper: Mapper<ThemeMode, ThemeModeDomainModel>,
) {
    operator fun invoke(
        themeModeDomainModel: ThemeModeDomainModel,
    ): Flow<OperationStatus.WithInputData<ThemeModeDomainModel, ThemeModeDomainModel>> = flow {
        try {
            emit(OperationStatus.WithInputData.Pending(themeModeDomainModel))

            emit(OperationStatus.WithInputData.Processing(themeModeDomainModel))

            val themeMode = themeModeDomainModelToThemeModeMapper.invoke(themeModeDomainModel)
            val updatedThemeMode = preferencesRepository.updateCurrentThemeMode(themeMode).unwrap()
            val updatedThemeModeDomainModel = themeModeToThemeModeDomainModelMapper.invoke(updatedThemeMode)

            emit(OperationStatus.WithInputData.Completed(themeModeDomainModel, updatedThemeModeDomainModel))
        }
        catch (throwable: Throwable) {
            emit(OperationStatus.WithInputData.Error(themeModeDomainModel, throwable))
        }
    }
}
