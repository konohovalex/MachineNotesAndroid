package ru.konohovalex.feature.account.presentation.profile.model

data class NotesStatistics(
    val notesAmount: Int,
    val audioFilesAmount: Int,
    val audioFilesWithSpeechRecognitionAmount: Int,
    val imagesAmount: Int,
    val imagesWithObjectRecognitionAmount: Int,
)
