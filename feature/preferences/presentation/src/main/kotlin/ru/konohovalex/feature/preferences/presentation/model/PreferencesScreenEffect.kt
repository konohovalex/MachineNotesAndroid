package ru.konohovalex.feature.preferences.presentation.model

import ru.konohovalex.core.presentation.arch.effect.Effect

sealed class PreferencesScreenEffect : Effect {
    data class EnableLanguageUpdates(val value: Boolean) : PreferencesScreenEffect()

    data class EnableThemeModeUpdates(val value: Boolean) : PreferencesScreenEffect()
}
