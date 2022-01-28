package ru.konohovalex.core.data.model

import java.lang.reflect.Type

data class TypeAdapterParams(
    val type: Type,
    val typeAdapter: Any,
)
