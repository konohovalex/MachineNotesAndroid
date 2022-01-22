package ru.konohovalex.feature.preferences.presentation.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import ru.konohovalex.core.presentation.arch.viewevent.ViewEventHandler
import ru.konohovalex.core.ui.compose.Tumbler
import ru.konohovalex.core.ui.compose.model.Position
import ru.konohovalex.core.ui.compose.model.TextWrapper
import ru.konohovalex.core.ui.compose.model.TumblerData
import ru.konohovalex.feature.preferences.presentation.R
import ru.konohovalex.feature.preferences.presentation.model.LanguageUiModel
import ru.konohovalex.feature.preferences.presentation.model.PreferencesScreenViewEffect
import ru.konohovalex.feature.preferences.presentation.model.PreferencesScreenViewEvent

@Composable
internal fun LanguageTumbler(
    currentLanguageUiModel: LanguageUiModel,
    availableLanguages: List<LanguageUiModel>,
    viewEventHandler: ViewEventHandler<PreferencesScreenViewEvent>,
    viewEffectState: State<PreferencesScreenViewEffect?>,
) {
    val viewEffectStateValue = viewEffectState.value

    val actionsEnabledState = remember {
        mutableStateOf(true)
    }
    if (viewEffectStateValue is PreferencesScreenViewEffect.EnableLanguageUpdates) {
        actionsEnabledState.value = viewEffectStateValue.value
    }

    val selectedLanguageState = remember {
        mutableStateOf<LanguageUiModel?>(null)
    }
    selectedLanguageState.value = currentLanguageUiModel

    val onSelectedLanguageChanged = { selectedLanguageUiModel: LanguageUiModel ->
        viewEventHandler.handle(PreferencesScreenViewEvent.UpdateCurrentLanguage(selectedLanguageUiModel))
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
