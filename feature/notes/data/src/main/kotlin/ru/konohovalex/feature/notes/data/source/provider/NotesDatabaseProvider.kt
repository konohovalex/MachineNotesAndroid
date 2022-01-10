package ru.konohovalex.feature.notes.data.source.provider

import android.content.Context
import androidx.room.Room
import ru.konohovalex.core.data.source.contract.provider.StorageProvider
import ru.konohovalex.feature.notes.data.source.storage.NotesDatabase
import javax.inject.Inject

internal class NotesDatabaseProvider
@Inject constructor() : StorageProvider<Context, NotesDatabase> {
    override fun provide(providerParams: Context): NotesDatabase = Room.databaseBuilder(
        providerParams.applicationContext,
        NotesDatabase::class.java,
        NotesDatabase.DATABASE_NAME,
    ).build()
}
