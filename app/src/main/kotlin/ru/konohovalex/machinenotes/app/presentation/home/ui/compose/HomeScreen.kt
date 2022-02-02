package ru.konohovalex.machinenotes.app.presentation.home.ui.compose

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ru.konohovalex.core.design.model.Theme
import ru.konohovalex.core.ui.compose.ThemedBottomNavigationBar
import ru.konohovalex.core.ui.model.BottomNavigationItem
import ru.konohovalex.feature.notes.presentation.navigation.getNoteListNavigationRoute
import ru.konohovalex.machinenotes.app.presentation.home.model.HomeBottomNavigationItem
import ru.konohovalex.machinenotes.app.presentation.navigation.bottomNavigationGraph

@Composable
internal fun HomeScreen(
    navController: NavController,
    onBackPressed: () -> Unit,
) {
    val bottomNavigationNavController = rememberNavController()

    BackHandler {
        onBackPressed.invoke()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.palette.backgroundColor),
    ) {
        Box(
            modifier = Modifier.weight(1f),
        ) {
            NavHost(
                navController = bottomNavigationNavController,
                startDestination = getNoteListNavigationRoute(),
            ) {
                bottomNavigationGraph(navController)
            }
        }

        ThemedBottomNavigationBar(
            bottomNavigationItems = listOf<BottomNavigationItem>(
                HomeBottomNavigationItem.NoteList,
                HomeBottomNavigationItem.Preferences,
                HomeBottomNavigationItem.Profile,
            ),
            selectedBottomNavigationItem = HomeBottomNavigationItem.NoteList,
            navController = bottomNavigationNavController,
        )
    }
}
