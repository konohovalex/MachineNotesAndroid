package ru.konohovalex.feature.account.presentation.profile.model

internal data class ProfileUiModel(
    val id: String,
    val name: String,
    val notesStatistics: NotesStatistics,
)
