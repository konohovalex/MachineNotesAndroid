package ru.konohovalex.feature.account.presentation.profile.model

import ru.konohovalex.core.presentation.arch.viewevent.ViewEvent

sealed class ProfileViewEvent : ViewEvent {
    object GetProfile : ProfileViewEvent()

    object LogOut : ProfileViewEvent()

    object DeleteAccount : ProfileViewEvent()

    object DeleteAllNotes : ProfileViewEvent()
}
