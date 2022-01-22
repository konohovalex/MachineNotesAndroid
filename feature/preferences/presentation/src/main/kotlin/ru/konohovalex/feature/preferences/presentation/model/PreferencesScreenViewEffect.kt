package ru.konohovalex.feature.preferences.presentation.model

import ru.konohovalex.core.presentation.arch.vieweffect.ViewEffect

sealed class PreferencesScreenViewEffect : ViewEffect {
    data class EnableLanguageUpdates(val value: Boolean) : PreferencesScreenViewEffect()

    data class EnableThemeModeUpdates(val value: Boolean) : PreferencesScreenViewEffect()
}
