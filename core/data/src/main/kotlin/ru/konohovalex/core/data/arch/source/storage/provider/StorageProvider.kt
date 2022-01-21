package ru.konohovalex.core.data.arch.source.storage.provider

import ru.konohovalex.core.data.arch.provider.Provider
import ru.konohovalex.core.data.arch.source.storage.Storage

fun interface StorageProvider<P, S : Storage> : Provider<P, S>
