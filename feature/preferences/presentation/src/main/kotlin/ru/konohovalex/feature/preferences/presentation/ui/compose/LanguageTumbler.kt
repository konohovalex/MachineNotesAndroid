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
import ru.konohovalex.feature.preferences.presentation.model.LanguageUiModel
import ru.konohovalex.feature.preferences.presentation.model.PreferencesScreenEffect
import ru.konohovalex.feature.preferences.presentation.model.PreferencesScreenEvent

@Composable
internal fun LanguageTumbler(
    currentLanguageUiModel: LanguageUiModel,
    availableLanguages: List<LanguageUiModel>,
    eventHandler: EventHandler<PreferencesScreenEvent>,
    effectState: State<PreferencesScreenEffect?>,
) {
    val effectStateValue = effectState.value

    val actionsEnabledState = remember {
        mutableStateOf(true)
    }
    if (effectStateValue is PreferencesScreenEffect.EnableLanguageUpdates) {
        actionsEnabledState.value = effectStateValue.value
    }

    val selectedLanguageState = remember {
        mutableStateOf<LanguageUiModel?>(null)
    }
    selectedLanguageState.value = currentLanguageUiModel

    val onSelectedLanguageChanged = { selectedLanguageUiModel: LanguageUiModel ->
        eventHandler.handle(PreferencesScreenEvent.UpdateCurrentLanguage(selectedLanguageUiModel))
    }

    Tumbler(
        tumblerData = TumblerData(
            titleTextWrapper = TextWrapper.StringResource(resourceId = R.string.language_tumbler_title),
            infoTextWrapper = TextWrapper.StringResource(resourceId = R.string.language_tumbler_info),
            positions = availableLanguages.map {
                Position.Text(
                    data = it,
                    textWrapper = it.textWrapper,
                )
            }
        ),
        actionsEnabledState = actionsEnabledState,
        selectedPositionDataState = selectedLanguageState,
        onSelectedPositionChanged = onSelectedLanguageChanged,
    )
}
