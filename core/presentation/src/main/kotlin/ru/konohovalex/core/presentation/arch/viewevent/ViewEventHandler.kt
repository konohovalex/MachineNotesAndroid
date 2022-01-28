package ru.konohovalex.core.presentation.arch.viewevent

fun interface ViewEventHandler<VE: ViewEvent> {
    fun handle(viewEvent: VE)
}
