package ru.konohovalex.feature.notes.presentation.list.navigation

import ru.konohovalex.core.data.navigation.Route

sealed class NotesRoute(override val route: String) : Route {
    object NoteList : NotesRoute("noteList")
}
