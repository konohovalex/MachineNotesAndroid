package ru.konohovalex.feature.account.presentation.auth.model

import androidx.annotation.StringRes

data class AuthDataUiModel(
    @StringRes
    val declineAuthorizationButtonTextResId: Int,
    val userNameUiModel: UserNameUiModel,
    val passwordUiModel: PasswordUiModel,
)
