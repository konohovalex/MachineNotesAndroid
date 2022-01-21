package ru.konohovalex.feature.preferences.data.repository.impl

import kotlinx.coroutines.delay
import ru.konohovalex.core.data.model.OperationResult
import ru.konohovalex.feature.preferences.data.model.Language
import ru.konohovalex.feature.preferences.data.model.ThemeMode
import ru.konohovalex.feature.preferences.data.repository.contract.PreferencesRepository
import ru.konohovalex.feature.preferences.data.source.storage.contract.PreferencesStorageContract
import javax.inject.Inject

internal class PreferencesRepositoryImpl
@Inject constructor(
    private val preferencesStorage: PreferencesStorageContract,
) : PreferencesRepository {
    override suspend fun getCurrentLanguage(): OperationResult<Language> =
        try {
            // tbd remove delays
            delay(1000)
            OperationResult.Success(preferencesStorage.getCurrentLanguage())
        }
        catch (throwable: Throwable) {
            OperationResult.Error(throwable)
        }

    override suspend fun updateCurrentLanguage(language: Language): OperationResult<Language> =
        try {
            // tbd remove delays
            delay(1000)
            OperationResult.Success(preferencesStorage.updateCurrentLanguage(language))
        }
        catch (throwable: Throwable) {
            OperationResult.Error(throwable)
        }

    override suspend fun getCurrentThemeMode(): OperationResult<ThemeMode> =
        try {
            // tbd remove delays
            delay(1000)
            OperationResult.Success(preferencesStorage.getCurrentThemeMode())
        }
        catch (throwable: Throwable) {
            OperationResult.Error(throwable)
        }

    override suspend fun updateCurrentThemeMode(themeMode: ThemeMode): OperationResult<ThemeMode> =
        try {
            // tbd remove delays
            delay(1000)
            OperationResult.Success(preferencesStorage.updateCurrentThemeMode(themeMode))
        }
        catch (throwable: Throwable) {
            OperationResult.Error(throwable)
        }
}
