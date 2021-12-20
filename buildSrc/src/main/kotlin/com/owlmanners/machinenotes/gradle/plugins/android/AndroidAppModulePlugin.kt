package com.owlmanners.machinenotes.gradle.plugins.android

import com.android.build.gradle.BaseExtension
import com.owlmanners.machinenotes.gradle.AppConfig
import com.owlmanners.machinenotes.gradle.plugins.Plugins
import com.owlmanners.machinenotes.gradle.utils.apply
import org.gradle.api.plugins.PluginContainer

class AndroidAppModulePlugin : AndroidModulePlugin() {
    override fun configurePlugins(pluginContainer: PluginContainer) {
        super.configurePlugins(pluginContainer)

        pluginContainer.apply(
            listOf(
                Plugins.androidApp,
            )
        )
    }

    override fun configureAndroid(androidExtension: BaseExtension) {
        super.configureAndroid(androidExtension)

        with(androidExtension) {
            defaultConfig {
                applicationId = AppConfig.applicationId
            }
        }
    }
}
