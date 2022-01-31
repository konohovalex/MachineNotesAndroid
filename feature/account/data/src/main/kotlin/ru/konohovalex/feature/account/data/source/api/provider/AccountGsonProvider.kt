package ru.konohovalex.feature.account.data.source.api.provider

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import ru.konohovalex.core.data.arch.provider.Provider
import javax.inject.Inject

internal class AccountGsonProvider
@Inject constructor() : Provider<Nothing?, Gson> {
    override fun provide(providerParams: Nothing?): Gson = GsonBuilder().create()
}
