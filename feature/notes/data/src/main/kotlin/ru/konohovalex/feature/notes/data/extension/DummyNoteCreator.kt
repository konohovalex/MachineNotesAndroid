package ru.konohovalex.feature.notes.data.extension

import ru.konohovalex.core.utils.model.DateTime
import ru.konohovalex.feature.notes.data.model.Note
import ru.konohovalex.feature.notes.data.model.NoteContent
import kotlin.random.Random

// tbd remove or move in tests folder
internal fun createDummyNoteList(itemsAmount: Int) = mutableListOf<Note>().apply {
    repeat(itemsAmount) {
        val mode = Random.nextInt(0, 3)

        add(
            createDummyNote(
                id = "$it",
                mode = mode,
            )
        )
    }
}

internal fun createDummyNote(
    id: String,
    mode: Int,
): Note {
    val dateTimeCreated = DateTime()
    val dateTimeLastEdited = DateTime()

    val noteContentOrderNumber = 1
    val noteContentId = "${id}_${noteContentOrderNumber}"

    return Note(
        id = id,
        dateTimeCreated = dateTimeCreated,
        dateTimeLastEdited = dateTimeLastEdited,
        noteContent = listOf(
            when (mode) {
                0 -> NoteContent.Text(
                    id = noteContentId,
                    "Заметка с очень длинным названием, которое не поместилось вот чуть-чуть\n" +
                            "И был этот текст крайне странным, и длина его превышала все границы адекватности\n" +
                            "В общем:\n" +
                            "Побывал я тут в Европе и повидал множество дивных вещей: архитектуру, людей"
                )
                1 -> NoteContent.Image(
                    id = noteContentId,
                    contentUrl = "",
                )
                else -> NoteContent.Audio(
                    id = noteContentId,
                    contentUrl = "",
                )
            }
        )
    )
}
