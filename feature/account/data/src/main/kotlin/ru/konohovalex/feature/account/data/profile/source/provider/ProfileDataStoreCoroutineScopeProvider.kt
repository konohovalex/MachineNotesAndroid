package ru.konohovalex.feature.account.data.profile.source.provider

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import ru.konohovalex.core.data.arch.provider.Provider

internal class ProfileDataStoreCoroutineScopeProvider : Provider<Nothing?, CoroutineScope> {
    override fun provide(providerParams: Nothing?): CoroutineScope =
        CoroutineScope(context = SupervisorJob() + Dispatchers.IO)
}
