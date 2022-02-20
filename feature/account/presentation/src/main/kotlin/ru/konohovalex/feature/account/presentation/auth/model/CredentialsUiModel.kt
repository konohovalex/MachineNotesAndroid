package ru.konohovalex.feature.account.presentation.auth.model

import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CredentialsUiModel(
    @StringRes
    val declineAuthorizationButtonTextResId: Int,
    val userNameUiModel: UserNameUiModel,
    val passwordUiModel: PasswordUiModel,
    val isPasswordVisible: Boolean = false,
): Parcelable
