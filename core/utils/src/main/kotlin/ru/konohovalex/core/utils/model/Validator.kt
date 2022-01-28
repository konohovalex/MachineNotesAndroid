package ru.konohovalex.core.utils.model

interface Validator<I, O> {
    fun validate(input: I): O
}
