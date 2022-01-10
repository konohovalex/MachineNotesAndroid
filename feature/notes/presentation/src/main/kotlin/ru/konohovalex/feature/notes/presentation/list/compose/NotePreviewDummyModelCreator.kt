package ru.konohovalex.feature.notes.presentation.list.compose

import ru.konohovalex.core.data.model.DateTime
import ru.konohovalex.feature.notes.presentation.R
import ru.konohovalex.feature.notes.presentation.list.model.NotePreviewUiModel
import ru.konohovalex.feature.notes.presentation.list.model.TextWrapper
import kotlin.random.Random

// tbd remove or move in tests folder
fun createNotePreviewDummyModelList(itemsAmount: Int): List<NotePreviewUiModel> {
    return mutableListOf<NotePreviewUiModel>().apply {
        repeat(itemsAmount) {
            val random = Random.nextInt(0, 4)
            add(
                createNotePreviewDummyModel("$it", random)
            )
        }
    }
}

internal fun createNotePreviewDummyModel(
    id: String,
    mode: Int,
) = NotePreviewUiModel(
    id = id,
    dateTimeLastEdited = DateTime(),
    titleTextWrapper = when (mode) {
        0, 1 -> TextWrapper.PlainText(
            "Заметка с очень длинным названием, которое не поместилось вот чуть-чуть"
        )
        else -> TextWrapper.StringResource(
            R.string.untitled
        )
    },
    subtitleTextWrapper = when (mode) {
        0 -> TextWrapper.PlainText(
            "И был этот текст крайне странным, и длина его превышала все границы адекватности"
        )
        1 -> TextWrapper.StringResource(
            R.string.image
        )
        2 -> TextWrapper.StringResource(
            R.string.audio
        )
        else -> TextWrapper.StringResource(
            R.string.no_additional_text
        )
    },
)
