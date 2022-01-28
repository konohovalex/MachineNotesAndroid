package ru.konohovalex.core.utils.extension

inline fun <reified O> Iterable<*>.firstOfClassOrNull() = find { it is O }?.safeCast<O>()
