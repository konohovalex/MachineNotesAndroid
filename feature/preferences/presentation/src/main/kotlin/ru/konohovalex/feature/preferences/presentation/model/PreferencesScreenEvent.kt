package ru.konohovalex.feature.preferences.presentation.model

import ru.konohovalex.core.presentation.arch.event.Event

internal sealed class PreferencesScreenEvent : Event {
    object GetPreferences : PreferencesScreenEvent()

    data class UpdateCurrentLanguage(val languageUiModel: LanguageUiModel) : PreferencesScreenEvent()

    data class UpdateCurrentThemeMode(val themeModeUiModel: ThemeModeUiModel) : PreferencesScreenEvent()
}
