package ru.konohovalex.core.data.arch.source.network.api.provider

import ru.konohovalex.core.data.arch.provider.Provider
import ru.konohovalex.core.data.arch.source.network.api.Api

fun interface ApiProvider<P, A : Api> : Provider<P, A>
