package ru.konohovalex.core.utils.extension

inline fun <reified O> Iterable<*>.firstOfClassOrNull() = find { it is O }?.safeCast<O>()

suspend inline fun <T> Iterable<T>.forEach(crossinline action: suspend (T) -> Unit) {
    forEach {
        action.invoke(it)
    }
}
