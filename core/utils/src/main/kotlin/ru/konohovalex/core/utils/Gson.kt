package ru.konohovalex.core.utils

import com.google.gson.Gson
import com.google.gson.JsonElement

inline fun <reified T> Gson.fromJson(json: String?) = fromJson(json, T::class.java)

inline fun <reified T> Gson.fromJson(json: JsonElement?) = fromJson(json, T::class.java)
