package ru.konohovalex.machinenotes.app.presentation.home.ui.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ru.konohovalex.core.design.model.Theme
import ru.konohovalex.feature.notes.presentation.navigation.getNoteListNavigationRoute
import ru.konohovalex.machinenotes.app.presentation.navigation.bottomNavigationGraph

@Composable
internal fun HomeScreen(
    navigateToNoteDetails: (noteId: String?) -> Unit,
    navigateToAuth: () -> Unit,
) {
    val bottomNavigationNavController = rememberNavController()
    val bottomNavigationStartDestination = remember {
        getNoteListNavigationRoute()
    }

    Scaffold(
        bottomBar = {
            HomeScreenBottomNavigationBar(bottomNavigationNavController)
        },
        backgroundColor = Theme.palette.backgroundColor,
        content = {
            NavHost(
                navController = bottomNavigationNavController,
                startDestination = bottomNavigationStartDestination,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
            ) {
                bottomNavigationGraph(navigateToNoteDetails, navigateToAuth)
            }
        }
    )
}
