package ru.konohovalex.feature.preferences.presentation.model

import ru.konohovalex.core.presentation.arch.viewevent.ViewEvent

internal sealed class PreferencesScreenViewEvent : ViewEvent {
    object GetPreferences : PreferencesScreenViewEvent()

    data class UpdateLanguage(val languageUiModel: LanguageUiModel) : PreferencesScreenViewEvent()

    data class UpdateThemeMode(val themeModeUiModel: ThemeModeUiModel) : PreferencesScreenViewEvent()
}
