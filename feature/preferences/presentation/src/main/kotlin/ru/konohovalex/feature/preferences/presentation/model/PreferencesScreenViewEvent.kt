package ru.konohovalex.feature.preferences.presentation.model

import ru.konohovalex.core.presentation.arch.viewevent.ViewEvent

internal sealed class PreferencesScreenViewEvent : ViewEvent {
    object GetPreferences : PreferencesScreenViewEvent()

    data class UpdateCurrentLanguage(val languageUiModel: LanguageUiModel) : PreferencesScreenViewEvent()

    data class UpdateCurrentThemeMode(val themeModeUiModel: ThemeModeUiModel) : PreferencesScreenViewEvent()
}
