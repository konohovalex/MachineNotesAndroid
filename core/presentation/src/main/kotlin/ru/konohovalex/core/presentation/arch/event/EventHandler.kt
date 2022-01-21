package ru.konohovalex.core.presentation.arch.event

fun interface EventHandler<T: Event> {
    fun handle(event: T)
}
