package ru.konohovalex.machinenotes.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ru.konohovalex.core.design.Theme
import ru.konohovalex.feature.notes.presentation.navigation.getNoteListNavigationRoute
import ru.konohovalex.feature.notes.presentation.navigation.noteListScreen
import ru.konohovalex.feature.preferences.presentation.navigation.preferencesScreen

@Composable
fun HomeScreen(homeNavController: NavController) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            HomeScreenBottomAppBar(navController)
        },
        backgroundColor = Theme.palette.backgroundColor,
        content = {
            NavHost(
                navController = navController,
                startDestination = getNoteListNavigationRoute(),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
            ) {
                noteListScreen(homeNavController)
                preferencesScreen()
            }
        }
    )
}
