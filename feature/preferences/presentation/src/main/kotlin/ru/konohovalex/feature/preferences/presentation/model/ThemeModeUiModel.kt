package ru.konohovalex.feature.preferences.presentation.model

import ru.konohovalex.core.ui.model.ImageWrapper
import ru.konohovalex.feature.preferences.presentation.R

enum class ThemeModeUiModel(val imageWrapper: ImageWrapper.ImageResource) {
    LIGHT(ImageWrapper.ImageResource(resourceId = R.drawable.ic_light_mode)),
    SYSTEM(ImageWrapper.ImageResource(resourceId = R.drawable.ic_device)),
    DARK(ImageWrapper.ImageResource(resourceId = R.drawable.ic_dark_mode)),
}
