package ru.konohovalex.feature.preferences.presentation.model

internal data class PreferencesUiModel(
    val availableLanguages: List<LanguageUiModel>,
    val currentLanguageUiModel: LanguageUiModel,
    val languageActionsEnabled: Boolean,
    val availableThemeModes: List<ThemeModeUiModel>,
    val currentThemeModeUiModel: ThemeModeUiModel,
    val themeModeActionsEnabled: Boolean,
)
