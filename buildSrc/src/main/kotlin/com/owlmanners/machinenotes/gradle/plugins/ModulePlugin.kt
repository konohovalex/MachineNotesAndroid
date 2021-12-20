package com.owlmanners.machinenotes.gradle.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.PluginContainer

abstract class ModulePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        configurePlugins(target.plugins)
        configureDependencies(target.dependencies)
    }

    abstract fun configurePlugins(pluginContainer: PluginContainer)

    abstract fun configureDependencies(dependencyHandler: DependencyHandler)
}
