package ru.konohovalex.machinenotes.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ru.konohovalex.core.design.Theme
import ru.konohovalex.core.ui.R
import ru.konohovalex.core.ui.compose.ThemedBottomAppBar
import ru.konohovalex.core.ui.compose.model.ImageWrapper
import ru.konohovalex.core.ui.compose.model.Position
import ru.konohovalex.feature.notes.presentation.navigation.getNoteListNavigationRoute
import ru.konohovalex.feature.notes.presentation.navigation.noteListScreen

@Composable
fun HomeScreen(
    homeNavController: NavController,
) {
    val bottomAppBarPositions = listOf(
        Position.Image(
            id = "0",
            imageWrapper = ImageWrapper.ImageResource(resourceId = R.drawable.ic_pensil),
        ),
        Position.Image(
            id = "1",
            imageWrapper = ImageWrapper.ImageResource(resourceId = R.drawable.ic_preferences),
        ),
    )
    val selectedBottomAppBarPositionIdState = remember {
        mutableStateOf("0")
    }
    val onSelectedPositionChanged = { positionId: String ->
        // tbd don't forget launchSingleTop = true
        selectedBottomAppBarPositionIdState.value = positionId
    }

    Scaffold(
        bottomBar = {
            ThemedBottomAppBar(
                positions = bottomAppBarPositions,
                selectedPositionIdState = selectedBottomAppBarPositionIdState,
                onSelectedPositionChanged = onSelectedPositionChanged,
            )
        },
        backgroundColor = Theme.palette.backgroundColor,
        content = {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = getNoteListNavigationRoute(),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
            ) {
                noteListScreen(navController = homeNavController)
            }
        }
    )
}
