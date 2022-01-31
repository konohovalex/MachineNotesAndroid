package ru.konohovalex.feature.preferences.data.source.provider

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import ru.konohovalex.core.data.arch.provider.Provider

internal class PreferencesDataStoreCoroutineScopeProvider : Provider<Nothing?, CoroutineScope> {
    override fun provide(providerParams: Nothing?): CoroutineScope =
        CoroutineScope(context = SupervisorJob() + Dispatchers.IO)
}
