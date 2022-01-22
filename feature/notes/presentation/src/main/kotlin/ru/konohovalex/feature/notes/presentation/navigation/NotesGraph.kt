package ru.konohovalex.feature.notes.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.konohovalex.feature.notes.presentation.details.ui.compose.NoteDetailsScreen
import ru.konohovalex.feature.notes.presentation.details.viewmodel.NoteDetailsViewModel
import ru.konohovalex.feature.notes.presentation.list.ui.compose.NoteListScreen
import ru.konohovalex.feature.notes.presentation.list.viewmodel.NoteListViewModel

fun getNoteListNavigationRoute() = NotesRoute.NoteList.buildNavigationRoute()

fun NavGraphBuilder.noteListScreen(navController: NavController) {
    composable(route = NotesRoute.NoteList.buildGraphRoute()) {
        val viewModel = hiltViewModel<NoteListViewModel>()
        NoteListScreen(
            viewEventHandler = viewModel,
            viewStateProvider = viewModel,
        ) {
            navController.navigate(
                route = NotesRoute.NoteDetails.buildNavigationRoute(noteId = it),
            )
        }
    }
}

fun NavGraphBuilder.noteDetailsScreen(navController: NavController) {
    val noteIdParameter = NotesRoute.NoteDetails.noteIdParameter
    composable(
        route = NotesRoute.NoteDetails.buildGraphRoute(),
        arguments = listOf(
            navArgument(name = noteIdParameter) {
                type = NavType.StringType
                nullable = true
            }
        ),
    ) { navBackStackEntry ->
        navBackStackEntry.arguments?.getString(noteIdParameter)?.let {
            val viewModel = hiltViewModel<NoteDetailsViewModel>()
            NoteDetailsScreen(
                noteId = it,
                navController = navController,
                viewEventHandler = viewModel,
                viewStateProvider = viewModel,
            )
        }
    }
}
