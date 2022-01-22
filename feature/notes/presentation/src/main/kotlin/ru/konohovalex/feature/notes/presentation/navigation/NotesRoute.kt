package ru.konohovalex.feature.notes.presentation.navigation

import ru.konohovalex.core.presentation.arch.navigation.NavigationParameter
import ru.konohovalex.core.presentation.arch.navigation.NavigationRoute

internal sealed class NotesRoute(override val destinations: List<String>) : NavigationRoute {
    companion object {
        private const val notes = "notes"
        private const val noteList = "noteList"
        private const val noteDetails = "noteDetails"
    }

    override val entryPoint = notes

    object NoteList : NotesRoute(destinations = listOf(noteList))

    object NoteDetails : NotesRoute(destinations = listOf(noteDetails)) {
        const val noteIdParameter = "noteId"

        fun buildGraphRoute() = super.buildGraphRoute(parameters = listOf(noteIdParameter))

        fun buildNavigationRoute(noteId: String?) = super.buildNavigationRoute(
            parameters = listOf(
                NavigationParameter(
                    key = noteIdParameter,
                    value = noteId,
                )
            ),
        )
    }
}
