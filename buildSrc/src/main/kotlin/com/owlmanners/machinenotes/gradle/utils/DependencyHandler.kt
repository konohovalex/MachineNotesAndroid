package com.owlmanners.machinenotes.gradle.utils

import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.kapt(dependencies: List<String>) {
    dependencies.forEach { dependency ->
        add("kapt", dependency)
    }
}

fun DependencyHandler.implementation(dependencies: List<Any>) {
    dependencies.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.androidTestImplementation(dependencies: List<String>) {
    dependencies.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.testImplementation(dependencies: List<String>) {
    dependencies.forEach { dependency ->
        add("testImplementation", dependency)
    }
}
