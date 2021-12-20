package ru.konohovalex.gradle.plugins

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.PluginContainer
import ru.konohovalex.gradle.Dependencies
import ru.konohovalex.gradle.utils.apply
import ru.konohovalex.gradle.utils.implementation
import ru.konohovalex.gradle.utils.testImplementation

open class KotlinModulePlugin : ModulePlugin() {
    override fun configurePlugins(pluginContainer: PluginContainer) {
        with(pluginContainer) {
            apply(
                listOf(
                    Plugins.kotlin,
                    Plugins.kapt,
                )
            )
        }
    }

    override fun configureDependencies(dependencyHandler: DependencyHandler) {
        with(dependencyHandler) {
            implementation(
                listOf(
                    Dependencies.Kotlin.stdLib,
                    Dependencies.Kotlin.Coroutines.core,
                )
            )

            testImplementation(
                listOf(
                    Dependencies.Testing.jUnit,
                    Dependencies.Kotlin.Coroutines.test,
                )
            )
        }
    }
}
