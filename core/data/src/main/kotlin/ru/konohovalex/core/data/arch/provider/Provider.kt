package ru.konohovalex.core.data.arch.provider

fun interface Provider<P, O> {
    fun provide(providerParams: P): O
}
