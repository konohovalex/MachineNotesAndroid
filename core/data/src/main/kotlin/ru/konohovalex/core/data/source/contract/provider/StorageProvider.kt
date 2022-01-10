package ru.konohovalex.core.data.source.contract.provider

import ru.konohovalex.core.data.source.contract.Storage

interface StorageProvider<P, S: Storage> : Provider<P, S>
