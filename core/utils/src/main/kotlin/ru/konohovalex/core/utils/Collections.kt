package ru.konohovalex.core.utils

inline fun <reified O> Iterable<*>.firstOfClassOrNull() = find { it is O }?.safeCast<O>()
