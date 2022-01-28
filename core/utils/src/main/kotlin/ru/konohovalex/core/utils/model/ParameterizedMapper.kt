package ru.konohovalex.core.utils.model

fun interface ParameterizedMapper<I, P, O> : (P) -> (I) -> O {
    override fun invoke(parameter: P): (input: I) -> O
}
