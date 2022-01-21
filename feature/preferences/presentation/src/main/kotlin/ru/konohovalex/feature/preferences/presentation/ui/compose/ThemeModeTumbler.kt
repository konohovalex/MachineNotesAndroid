package ru.konohovalex.feature.preferences.presentation.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import ru.konohovalex.core.presentation.arch.event.EventHandler
import ru.konohovalex.core.ui.compose.Tumbler
import ru.konohovalex.core.ui.compose.model.Position
import ru.konohovalex.core.ui.compose.model.TextWrapper
import ru.konohovalex.core.ui.compose.model.TumblerData
import ru.konohovalex.feature.preferences.presentation.R
import ru.konohovalex.feature.preferences.presentation.model.PreferencesScreenEffect
import ru.konohovalex.feature.preferences.presentation.model.PreferencesScreenEvent
import ru.konohovalex.feature.preferences.presentation.model.ThemeModeUiModel

@Composable
internal fun ThemeModeTumbler(
    currentThemeModeUiModel: ThemeModeUiModel,
    availableThemeModes: List<ThemeModeUiModel>,
    eventHandler: EventHandler<PreferencesScreenEvent>,
    effectState: State<PreferencesScreenEffect?>,
) {
    val effectStateValue = effectState.value

    val actionsEnabledState = remember {
        mutableStateOf(true)
    }
    if (effectStateValue is PreferencesScreenEffect.EnableThemeModeUpdates) {
        actionsEnabledState.value = effectStateValue.value
    }

    val selectedThemeModeState = remember {
        mutableStateOf<ThemeModeUiModel?>(null)
    }
    selectedThemeModeState.value = currentThemeModeUiModel

    val onSelectedThemeModeChanged = { selectedThemeModeUiModel: ThemeModeUiModel ->
        eventHandler.handle(PreferencesScreenEvent.UpdateCurrentThemeMode(selectedThemeModeUiModel))
    }

    Tumbler(
        tumblerData = TumblerData(
            titleTextWrapper = TextWrapper.StringResource(resourceId = R.string.theme_mode_tumbler_title),
            positions = availableThemeModes.map {
                Position.Image(
                    data = it,
                    imageWrapper = it.imageWrapper,
                )
            }
        ),
        actionsEnabledState = actionsEnabledState,
        selectedPositionDataState = selectedThemeModeState,
        onSelectedPositionChanged = onSelectedThemeModeChanged,
    )
}
