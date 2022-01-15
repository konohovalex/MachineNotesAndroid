package ru.konohovalex.machinenotes.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.konohovalex.feature.notes.presentation.navigation.noteDetailsScreen
import ru.konohovalex.machinenotes.compose.HomeScreen

internal fun NavGraphBuilder.mainGraph(navController: NavController) {
    composable(route = MainRoute.Home.buildGraphRoute()) {
        HomeScreen(homeNavController = navController)
    }

    noteDetailsScreen()
}
