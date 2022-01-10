package ru.konohovalex.gradle.plugins.configuration

import org.gradle.api.artifacts.dsl.DependencyHandler
import ru.konohovalex.gradle.Dependencies
import ru.konohovalex.gradle.utils.implementation
import ru.konohovalex.gradle.utils.kapt

class DaggerConfigurator {
    fun configureDependencies(dependencyHandler: DependencyHandler) {
        with(dependencyHandler) {
            implementation(Dependencies.Dagger.core)

            kapt(Dependencies.Dagger.compiler)
        }
    }
}
