package ru.konohovalex.feature.notes.data.source.storage.database.provider

import android.content.Context
import androidx.room.Room
import ru.konohovalex.core.data.arch.provider.Provider
import ru.konohovalex.feature.notes.data.source.storage.database.NotesDatabase
import javax.inject.Inject

internal class NotesDatabaseProvider
@Inject constructor() : Provider<Context, NotesDatabase> {
    override fun provide(providerParams: Context): NotesDatabase = Room.databaseBuilder(
        providerParams.applicationContext,
        NotesDatabase::class.java,
        NotesDatabase.DATABASE_NAME,
    ).build()
}
