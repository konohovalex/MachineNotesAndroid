package ru.konohovalex.core.utils.model

fun interface Mapper<I, O> : (I) -> O {
    override fun invoke(input: I): O
}
