package ru.konohovalex.gradle.utils

import org.gradle.api.artifacts.dsl.DependencyHandler

private fun DependencyHandler.addConfiguration(
    configurationName: String,
    vararg dependencies: Any,
) {
    dependencies.forEach { dependency ->
        (dependency as? List<Any>)?.forEach {
            add(configurationName, it)
        } ?: add(configurationName, dependency)
    }
}

fun DependencyHandler.kapt(vararg dependencies: Any) {
    addConfiguration(
        configurationName = "kapt",
        dependencies = dependencies,
    )
}

fun DependencyHandler.implementation(vararg dependencies: Any) {
    addConfiguration(
        configurationName = "implementation",
        dependencies = dependencies,
    )
}

fun DependencyHandler.androidTestImplementation(vararg dependencies: Any) {
    addConfiguration(
        configurationName = "androidTestImplementation",
        dependencies = dependencies,
    )
}

fun DependencyHandler.testImplementation(vararg dependencies: Any) {
    addConfiguration(
        configurationName = "testImplementation",
        dependencies = dependencies,
    )
}
