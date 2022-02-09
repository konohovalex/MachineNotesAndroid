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
            navigateToNoteDetails = { noteId: String? ->
                navController.navigate(getNoteDetailsNavigationRoute(noteId))
            },
        )
    }
}

fun getNoteDetailsNavigationRoute(noteId: String?) = NotesRoute.NoteDetails.buildNavigationRoute(noteId)

fun NavGraphBuilder.noteDetailsScreen(navigateBack: () -> Unit) {
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
        val viewModel = hiltViewModel<NoteDetailsViewModel>()

        NoteDetailsScreen(
            noteId = navBackStackEntry.arguments?.getString(noteIdParameter),
            viewEventHandler = viewModel,
            viewStateProvider = viewModel,
            navigateBack = navigateBack,
        )
    }
}
