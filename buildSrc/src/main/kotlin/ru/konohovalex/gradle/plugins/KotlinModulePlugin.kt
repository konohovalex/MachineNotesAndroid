package ru.konohovalex.gradle.plugins

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.PluginContainer
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import ru.konohovalex.gradle.Dependencies
import ru.konohovalex.gradle.utils.apply
import ru.konohovalex.gradle.utils.implementation
import ru.konohovalex.gradle.utils.testImplementation

open class KotlinModulePlugin : ModulePlugin() {
    override fun apply(target: Project) {
        super.apply(target)

        target.tasks.withType<KotlinCompile>().configureEach {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_1_8.toString()
            }
        }
    }

    override fun configurePlugins(pluginContainer: PluginContainer) {
        with(pluginContainer) {
            apply(
                Plugins.kotlin,
                Plugins.kapt,
            )
        }
    }

    override fun configureDependencies(dependencyHandler: DependencyHandler) {
        with(dependencyHandler) {
            implementation(
                Dependencies.Kotlin.stdLib,
                Dependencies.Kotlin.Coroutines.core,
            )

            testImplementation(
                Dependencies.Testing.jUnit,
                Dependencies.Kotlin.Coroutines.test,
            )
        }
    }
}
