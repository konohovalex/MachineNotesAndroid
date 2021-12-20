package com.owlmanners.machinenotes.gradle.plugins.android

import com.owlmanners.machinenotes.gradle.plugins.Plugins
import com.owlmanners.machinenotes.gradle.utils.apply
import org.gradle.api.plugins.PluginContainer

abstract class AndroidLibraryModulePlugin : AndroidModulePlugin() {
    override fun configurePlugins(pluginContainer: PluginContainer) {
        super.configurePlugins(pluginContainer)

        pluginContainer.apply(
            listOf(
                Plugins.androidLibrary,
            )
        )
    }
}
