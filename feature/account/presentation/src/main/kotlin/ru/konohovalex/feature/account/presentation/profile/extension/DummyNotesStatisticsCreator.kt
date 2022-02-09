package ru.konohovalex.feature.account.presentation.profile.extension

import ru.konohovalex.feature.account.presentation.profile.model.NotesStatistics

// tbd remove
internal fun createDummyNotesStatistics() =
    NotesStatistics(
        notesAmount = 128,
        audioFilesAmount = 64,
        audioFilesWithSpeechRecognitionAmount = 32,
        imagesAmount = 16,
        imagesWithObjectRecognitionAmount = 8,
    )
