package com.owlmanners.machinenotes.gradle.plugins.android

import com.owlmanners.machinenotes.gradle.Dependencies
import com.owlmanners.machinenotes.gradle.plugins.Plugins
import com.owlmanners.machinenotes.gradle.utils.apply
import com.owlmanners.machinenotes.gradle.utils.implementation
import com.owlmanners.machinenotes.gradle.utils.kapt
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.PluginContainer
import org.jetbrains.kotlin.gradle.plugin.KaptExtension

class FeatureModulePlugin : AndroidLibraryModulePlugin() {
    override fun apply(target: Project) {
        super.apply(target)

        configureKapt(target.extensions.getByType(KaptExtension::class.java))
    }

    override fun configurePlugins(pluginContainer: PluginContainer) {
        super.configurePlugins(pluginContainer)

        pluginContainer.apply(
            listOf(
                Plugins.hilt,
            )
        )
    }

    override fun configureDependencies(dependencyHandler: DependencyHandler) {
        super.configureDependencies(dependencyHandler)

        with(dependencyHandler) {
            implementation(
                mutableListOf<String>().apply {
                    addAll(Dependencies.Hilt.getAllRuntimeDependencies())
                }
            )

            kapt(
                listOf(
                    Dependencies.Hilt.compiler,
                )
            )
        }
    }

    private fun configureKapt(kaptExtension: KaptExtension) {
        kaptExtension.correctErrorTypes = true
    }
}
