package ru.konohovalex.feature.account.presentation.auth.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@JvmInline
@Parcelize
value class UserNameUiModel(val value: String): Parcelable
