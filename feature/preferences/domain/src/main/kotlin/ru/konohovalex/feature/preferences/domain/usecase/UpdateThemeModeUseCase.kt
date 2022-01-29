package ru.konohovalex.feature.preferences.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.core.utils.model.OperationStatus
import ru.konohovalex.feature.preferences.data.model.ThemeMode
import ru.konohovalex.feature.preferences.data.repository.contract.PreferencesRepositoryContract
import ru.konohovalex.feature.preferences.domain.model.ThemeModeDomainModel
import javax.inject.Inject

class UpdateThemeModeUseCase
@Inject constructor(
    private val preferencesRepository: PreferencesRepositoryContract,
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
            val updatedThemeMode = preferencesRepository.updateThemeMode(themeMode)
            val updatedThemeModeDomainModel = themeModeToThemeModeDomainModelMapper.invoke(updatedThemeMode)

            emit(OperationStatus.WithInputData.Completed(themeModeDomainModel, updatedThemeModeDomainModel))
        }
        catch (throwable: Throwable) {
            emit(OperationStatus.WithInputData.Error(themeModeDomainModel, throwable))
        }
    }
}
