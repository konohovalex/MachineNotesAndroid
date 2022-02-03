package ru.konohovalex.machinenotes.app.presentation.main.model

import ru.konohovalex.core.presentation.arch.viewstate.ErrorViewState
import ru.konohovalex.core.presentation.arch.viewstate.ViewState
import ru.konohovalex.feature.preferences.presentation.model.LanguageUiModel
import ru.konohovalex.feature.preferences.presentation.model.ThemeModeUiModel

sealed class MainViewState : ViewState {
    abstract val languageUiModel: LanguageUiModel?
    abstract val themeModeUiModel: ThemeModeUiModel?

    object Idle : MainViewState() {
        override val languageUiModel: LanguageUiModel? = null
        override val themeModeUiModel: ThemeModeUiModel? = null
    }

    data class Loading(
        override val languageUiModel: LanguageUiModel?,
        override val themeModeUiModel: ThemeModeUiModel?,
    ) : MainViewState()

    data class FirstLaunch(
        override val languageUiModel: LanguageUiModel?,
        override val themeModeUiModel: ThemeModeUiModel?,
    ) : MainViewState()

    data class NotFirstLaunch(
        override val languageUiModel: LanguageUiModel?,
        override val themeModeUiModel: ThemeModeUiModel?,
    ) : MainViewState()

    data class Error(
        override val languageUiModel: LanguageUiModel?,
        override val themeModeUiModel: ThemeModeUiModel?,
        override val throwable: Throwable,
        override val onActionButtonClick: () -> Unit,
    ) : MainViewState(), ErrorViewState
}
