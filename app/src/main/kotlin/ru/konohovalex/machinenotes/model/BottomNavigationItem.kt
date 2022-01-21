package ru.konohovalex.machinenotes.model

import ru.konohovalex.core.ui.compose.model.ImageWrapper
import ru.konohovalex.machinenotes.R

enum class BottomNavigationItem(val imageWrapper: ImageWrapper) {
    NOTE_LIST(ImageWrapper.ImageResource(resourceId = R.drawable.ic_notes)),
    PREFERENCES(ImageWrapper.ImageResource(resourceId = R.drawable.ic_preferences)),
}
