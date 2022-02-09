package ru.konohovalex.feature.preferences.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Named

@Module
@DisableInstallInCheck
class PreferencesDataCoroutinesModule {
    @Provides
    @Named(Qualifiers.PREFERENCES_DATA_COROUTINE_SCOPE)
    fun providePreferencesDataCoroutineScope(): CoroutineScope = CoroutineScope(context = SupervisorJob() + Dispatchers.IO)
}
