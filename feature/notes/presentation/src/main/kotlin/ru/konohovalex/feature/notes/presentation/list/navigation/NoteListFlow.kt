package ru.konohovalex.feature.notes.presentation.list.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.konohovalex.feature.notes.presentation.list.compose.NoteListScreen

fun NavGraphBuilder.noteListFlow(
    navController: NavController,
) {
    val noteListRoute = NotesRoute.NoteList.route
    composable(route = noteListRoute) {
        NoteListScreen()
    }
}
