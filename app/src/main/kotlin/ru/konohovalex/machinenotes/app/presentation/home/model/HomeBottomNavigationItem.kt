package ru.konohovalex.machinenotes.app.presentation.home.model

import ru.konohovalex.core.ui.model.BottomNavigationItem
import ru.konohovalex.core.ui.model.ImageWrapper
import ru.konohovalex.feature.account.presentation.navigation.getProfileNavigationRoute
import ru.konohovalex.feature.notes.presentation.navigation.getNoteListNavigationRoute
import ru.konohovalex.feature.preferences.presentation.navigation.getPreferencesNavigationRoute
import ru.konohovalex.machinenotes.R

sealed class HomeBottomNavigationItem(
    override val navigationRoute: String,
    override val imageWrapper: ImageWrapper,
) : BottomNavigationItem {
    object NoteList : HomeBottomNavigationItem(
        navigationRoute = getNoteListNavigationRoute(),
        imageWrapper = ImageWrapper.ImageResource(resourceId = R.drawable.ic_notes),
    )

    object Preferences : HomeBottomNavigationItem(
        navigationRoute = getPreferencesNavigationRoute(),
        imageWrapper = ImageWrapper.ImageResource(resourceId = R.drawable.ic_preferences),
    )

    object Profile : HomeBottomNavigationItem(
        navigationRoute = getProfileNavigationRoute(),
        imageWrapper = ImageWrapper.ImageResource(resourceId = R.drawable.ic_profile),
    )
}
