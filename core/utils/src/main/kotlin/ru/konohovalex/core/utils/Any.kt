package ru.konohovalex.core.utils

inline fun <reified T> Any?.safeCast() = this as? T
