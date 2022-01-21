package ru.konohovalex.core.data.arch.source.api.provider

import ru.konohovalex.core.data.arch.provider.Provider
import ru.konohovalex.core.data.arch.source.api.Api

fun interface ApiProvider<P, A : Api> : Provider<P, A>
