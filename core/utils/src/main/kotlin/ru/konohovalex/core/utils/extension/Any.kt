package ru.konohovalex.core.utils.extension

inline fun <reified T> Any?.safeCast() = this as? T

inline fun <T, T1 : T, R> runIfInstance(instance: T, clazz: Class<T1>, block: (T1) -> R) {
    try {
        if (clazz.isInstance(instance)) (instance as T1).let(block)
    }
    catch (classCastException: ClassCastException) {
    }
}
