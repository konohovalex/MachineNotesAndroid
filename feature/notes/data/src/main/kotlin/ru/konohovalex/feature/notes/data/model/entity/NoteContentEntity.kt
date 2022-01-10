package ru.konohovalex.feature.notes.data.model.entity

/*@Entity(
    tableName = NoteContentEntity.TABLE_NAME,
)*/
internal sealed class NoteContentEntity(open val id: String) {
    companion object {
        const val TABLE_NAME = "note_contents"
    }

    data class Text(
        override val id: String,
        val content: String,
    ) : NoteContentEntity(id)

    data class Image(
        override val id: String,
        val contentUrl: String,
    ) : NoteContentEntity(id)

    data class Audio(
        override val id: String,
        val contentUrl: String,
    ) : NoteContentEntity(id)
}
