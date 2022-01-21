package ru.konohovalex.feature.notes.data.source.storage.provider

import android.content.Context
import androidx.room.Room
import ru.konohovalex.core.data.arch.source.storage.provider.StorageProvider
import ru.konohovalex.feature.notes.data.source.storage.NotesStorage
import javax.inject.Inject

internal class NotesStorageProvider
@Inject constructor() : StorageProvider<Context, NotesStorage> {
    override fun provide(providerParams: Context): NotesStorage = Room.databaseBuilder(
        providerParams.applicationContext,
        NotesStorage::class.java,
        NotesStorage.DATABASE_NAME,
    ).build()
}
