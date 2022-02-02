package ru.konohovalex.core.ui.model

import android.os.Parcelable

interface BottomNavigationItem : Parcelable {
    val navigationRoute: String
    val imageWrapper: ImageWrapper
}
