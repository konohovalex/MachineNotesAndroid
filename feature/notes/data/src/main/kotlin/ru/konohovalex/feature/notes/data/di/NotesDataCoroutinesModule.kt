package ru.konohovalex.feature.notes.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Named

@Module
@DisableInstallInCheck
class NotesDataCoroutinesModule {
    @Provides
    @Named(Qualifiers.NOTES_DATA_COROUTINES_SCOPE)
    fun provideCoroutineScope(): CoroutineScope = CoroutineScope(context = SupervisorJob() + Dispatchers.IO)
}
