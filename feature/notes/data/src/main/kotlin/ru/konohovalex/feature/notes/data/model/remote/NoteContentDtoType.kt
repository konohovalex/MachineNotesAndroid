package ru.konohovalex.feature.notes.data.model.remote

enum class NoteContentDtoType(val serializedName: String) {
    TEXT("text"),
    IMAGE("image"),
    AUDIO("audio"),
}
