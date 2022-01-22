package ru.konohovalex.core.presentation.arch.viewevent

fun interface ViewEventHandler<T: ViewEvent> {
    fun handle(viewEvent: T)
}
