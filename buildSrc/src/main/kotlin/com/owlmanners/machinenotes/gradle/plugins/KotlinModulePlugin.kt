package com.owlmanners.machinenotes.gradle.plugins

import com.owlmanners.machinenotes.gradle.Dependencies
import com.owlmanners.machinenotes.gradle.utils.apply
import com.owlmanners.machinenotes.gradle.utils.implementation
import com.owlmanners.machinenotes.gradle.utils.testImplementation
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.PluginContainer

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
