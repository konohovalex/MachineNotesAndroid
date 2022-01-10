package ru.konohovalex.gradle.utils

import org.gradle.api.plugins.PluginContainer

fun PluginContainer.apply(vararg ids: String) = ids.forEach(::apply)
