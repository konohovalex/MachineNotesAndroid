package ru.konohovalex.feature.preferences.presentation.model

import ru.konohovalex.core.presentation.arch.viewevent.ViewEvent

internal sealed class PreferencesViewEvent : ViewEvent {
    object GetPreferences : PreferencesViewEvent()

    data class UpdateLanguage(val languageUiModel: LanguageUiModel) : PreferencesViewEvent()

    data class UpdateThemeMode(val themeModeUiModel: ThemeModeUiModel) : PreferencesViewEvent()

    object ErrorProcessed : PreferencesViewEvent()
}
