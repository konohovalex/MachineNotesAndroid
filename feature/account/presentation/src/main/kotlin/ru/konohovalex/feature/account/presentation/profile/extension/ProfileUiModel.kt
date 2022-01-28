package ru.konohovalex.feature.account.presentation.profile.extension

import ru.konohovalex.feature.account.presentation.profile.model.ProfileUiModel

internal fun ProfileUiModel.isAuthorized() = name.isNotBlank()
