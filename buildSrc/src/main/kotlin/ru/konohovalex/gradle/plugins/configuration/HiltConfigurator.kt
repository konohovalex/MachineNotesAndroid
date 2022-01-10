package ru.konohovalex.gradle.plugins.configuration

import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.PluginContainer
import org.jetbrains.kotlin.gradle.plugin.KaptExtension
import ru.konohovalex.gradle.Dependencies
import ru.konohovalex.gradle.plugins.Plugins
import ru.konohovalex.gradle.utils.implementation
import ru.konohovalex.gradle.utils.kapt

class HiltConfigurator {
    fun configureKapt(target: Project) {
        target.extensions.getByType(KaptExtension::class.java).apply {
            correctErrorTypes = true
        }
    }

    fun configurePlugins(pluginContainer: PluginContainer) {
        pluginContainer.apply(Plugins.hilt)
    }

    fun configureDependencies(dependencyHandler: DependencyHandler) {
        with(dependencyHandler) {
            implementation(Dependencies.Hilt.core)

            kapt(Dependencies.Hilt.compiler)
        }
    }
}
