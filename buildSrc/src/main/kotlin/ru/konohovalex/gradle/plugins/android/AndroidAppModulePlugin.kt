package ru.konohovalex.gradle.plugins.android

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.PluginContainer
import ru.konohovalex.gradle.AppConfig
import ru.konohovalex.gradle.plugins.Plugins
import ru.konohovalex.gradle.plugins.configuration.ComposeConfigurator
import ru.konohovalex.gradle.plugins.configuration.HiltConfigurator
import ru.konohovalex.gradle.utils.apply

class AndroidAppModulePlugin : AndroidModulePlugin() {
    private val composeConfigurator = ComposeConfigurator()
    private val hiltConfigurator = HiltConfigurator()

    override fun apply(target: Project) {
        super.apply(target)

        configureAndroidApp(target.extensions.getByType(BaseAppModuleExtension::class.java))

        hiltConfigurator.configureKapt(target)
    }

    override fun configurePlugins(pluginContainer: PluginContainer) {
        pluginContainer.apply(
            Plugins.androidApp,
        )

        hiltConfigurator.configurePlugins(pluginContainer)

        super.configurePlugins(pluginContainer)
    }

    override fun configureDependencies(dependencyHandler: DependencyHandler) {
        super.configureDependencies(dependencyHandler)

        hiltConfigurator.configureDependencies(dependencyHandler)
    }

    private fun configureAndroidApp(baseAppModuleExtension: BaseAppModuleExtension) {
        with(baseAppModuleExtension) {
            defaultConfig {
                applicationId = AppConfig.applicationId

                versionCode = AppConfig.versionCode
                versionName = AppConfig.versionName
            }

            buildTypes {
                debug {
                    isMinifyEnabled = false
                }
                release {
                    isMinifyEnabled = true
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }
            }
        }

        composeConfigurator.configureCompose(baseAppModuleExtension)
    }
}
