package ru.konohovalex.gradle.utils

import org.gradle.api.plugins.PluginContainer

fun PluginContainer.apply(ids: List<String>) = ids.forEach(::apply)
