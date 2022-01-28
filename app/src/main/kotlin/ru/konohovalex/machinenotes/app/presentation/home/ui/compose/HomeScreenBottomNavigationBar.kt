package ru.konohovalex.machinenotes.app.presentation.home.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import ru.konohovalex.core.ui.compose.ThemedBottomNavigationBar
import ru.konohovalex.core.ui.model.BottomNavigationItem
import ru.konohovalex.machinenotes.app.presentation.home.model.HomeBottomNavigationItem

@Composable
internal fun HomeScreenBottomNavigationBar(navController: NavController) {
    val bottomAppBarPositions = remember {
        listOf<BottomNavigationItem>(
            HomeBottomNavigationItem.NoteList,
            HomeBottomNavigationItem.Preferences,
            HomeBottomNavigationItem.Profile,
        )
    }

    ThemedBottomNavigationBar(
        bottomNavigationItems = bottomAppBarPositions,
        selectedBottomNavigationItem = HomeBottomNavigationItem.NoteList,
        navController = navController,
    )
}
