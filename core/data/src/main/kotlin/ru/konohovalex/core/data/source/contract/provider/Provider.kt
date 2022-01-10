package ru.konohovalex.core.data.source.contract.provider

interface Provider<P, O> {
    fun provide(providerParams: P): O
}
