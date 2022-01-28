package ru.konohovalex.feature.notes.presentation.extension

import ru.konohovalex.core.utils.model.DateTime
import ru.konohovalex.feature.notes.presentation.R
import ru.konohovalex.feature.notes.presentation.list.model.NotePreviewUiModel
import ru.konohovalex.core.ui.model.TextWrapper
import kotlin.random.Random

// tbd remove or move in tests folder
internal fun createNotePreviewDummyModelList(itemsAmount: Int): List<NotePreviewUiModel> {
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
        0, 1 -> TextWrapper.PlainText(value = "Заметка с очень длинным названием, которое не поместилось вот чуть-чуть")
        else -> TextWrapper.StringResource(resourceId = R.string.untitled)
    },
    subtitleTextWrapper = when (mode) {
        0 -> TextWrapper.PlainText(value = "И был этот текст крайне странным, и длина его превышала все границы адекватности")
        1 -> TextWrapper.StringResource(resourceId = R.string.image)
        2 -> TextWrapper.StringResource(resourceId = R.string.audio)
        else -> TextWrapper.StringResource(resourceId = R.string.no_additional_text)
    },
)
