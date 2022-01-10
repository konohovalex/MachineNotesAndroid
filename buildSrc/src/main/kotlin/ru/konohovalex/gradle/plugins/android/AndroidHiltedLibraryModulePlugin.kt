package ru.konohovalex.gradle.plugins.android

import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.PluginContainer
import ru.konohovalex.gradle.plugins.configuration.HiltConfigurator

open class AndroidHiltedLibraryModulePlugin : AndroidLibraryModulePlugin() {
    private val hiltConfigurator = HiltConfigurator()

    override fun apply(target: Project) {
        super.apply(target)

        hiltConfigurator.configureKapt(target)
    }

    override fun configurePlugins(pluginContainer: PluginContainer) {
        super.configurePlugins(pluginContainer)

        hiltConfigurator.configurePlugins(pluginContainer)
    }

    override fun configureDependencies(dependencyHandler: DependencyHandler) {
        super.configureDependencies(dependencyHandler)

        hiltConfigurator.configureDependencies(dependencyHandler)
    }
}
