package ru.konohovalex.core.data.extension

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import ru.konohovalex.core.data.model.TypeAdapterParams

fun createGsonWithTypeAdapters(typeAdapterParams: List<TypeAdapterParams>): Gson = GsonBuilder()
    .apply {
        typeAdapterParams.forEach {
            registerTypeAdapter(it.type, it.typeAdapter)
        }
    }
    .create()
