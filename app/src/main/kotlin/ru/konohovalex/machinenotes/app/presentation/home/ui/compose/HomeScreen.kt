package ru.konohovalex.machinenotes.app.presentation.home.ui.compose

import androidx.compose.foundation.layout.Column
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
        backgroundColor = Theme.palette.backgroundColor,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
            ) {
                NavHost(
                    modifier = Modifier.weight(1f),
                    navController = bottomNavigationNavController,
                    startDestination = bottomNavigationStartDestination,
                ) {
                    bottomNavigationGraph(navigateToNoteDetails, navigateToAuth)
                }

                /** Bottom navigation bar should've been in [Scaffold]'s topBar attribute,
                 * but for completely unknown reason it ain't reacting on [Theme.palette] changes */
                HomeScreenBottomNavigationBar(bottomNavigationNavController)
            }
        }
    )
}
