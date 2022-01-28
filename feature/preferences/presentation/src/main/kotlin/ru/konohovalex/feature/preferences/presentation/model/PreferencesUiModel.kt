package ru.konohovalex.feature.preferences.presentation.model

import ru.konohovalex.core.ui.model.TumblerData

internal data class PreferencesUiModel(
    val availableLanguagesTumblerData: TumblerData<LanguageUiModel>,
    val currentLanguageUiModel: LanguageUiModel,
    val languageActionsEnabled: Boolean,
    val availableThemeModesTumblerData: TumblerData<ThemeModeUiModel>,
    val currentThemeModeUiModel: ThemeModeUiModel,
    val themeModeActionsEnabled: Boolean,
)
