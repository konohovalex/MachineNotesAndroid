package ru.konohovalex.machinenotes.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import ru.konohovalex.core.ui.compose.ThemedBottomAppBar
import ru.konohovalex.core.ui.compose.model.Position
import ru.konohovalex.feature.notes.presentation.navigation.getNoteListNavigationRoute
import ru.konohovalex.feature.preferences.presentation.navigation.getPreferencesNavigationRoute
import ru.konohovalex.machinenotes.model.BottomNavigationItem

@Composable
internal fun HomeScreenBottomAppBar(navController: NavController) {
    val bottomAppBarPositions = BottomNavigationItem.values().map {
        Position.Image(
            data = it,
            imageWrapper = it.imageWrapper,
        )
    }
    val selectedBottomAppBarPositionDataState = remember {
        mutableStateOf(BottomNavigationItem.NOTE_LIST)
    }
    val onSelectedPositionChanged = { bottomNavigationItem: BottomNavigationItem ->
        selectedBottomAppBarPositionDataState.value = bottomNavigationItem
        when (selectedBottomAppBarPositionDataState.value) {
            BottomNavigationItem.NOTE_LIST ->
                navController.navigate(route = getNoteListNavigationRoute()) {
                    // tbd something is wrong
                    launchSingleTop = true
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    restoreState = true
                }
            BottomNavigationItem.PREFERENCES ->
                navController.navigate(route = getPreferencesNavigationRoute()) {
                    // tbd something is wrong
                    launchSingleTop = true
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    restoreState = true
                }
        }
    }

    ThemedBottomAppBar(
        positions = bottomAppBarPositions,
        selectedPositionDataState = selectedBottomAppBarPositionDataState,
        onSelectedPositionChanged = onSelectedPositionChanged,
    )
}
