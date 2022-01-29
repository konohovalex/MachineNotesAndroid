package ru.konohovalex.machinenotes.app.presentation.main.model

import ru.konohovalex.core.presentation.arch.viewevent.ViewEvent

sealed class MainViewEvent : ViewEvent {
    object Init : MainViewEvent()

    object FirstLaunchCompleted : MainViewEvent()
}
