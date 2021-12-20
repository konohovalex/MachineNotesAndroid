package ru.konohovalex.gradle.plugins.android

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.PluginContainer
import ru.konohovalex.gradle.AppConfig
import ru.konohovalex.gradle.Dependencies
import ru.konohovalex.gradle.plugins.Plugins
import ru.konohovalex.gradle.utils.apply
import ru.konohovalex.gradle.utils.implementation
import ru.konohovalex.gradle.utils.kapt

class AndroidAppModulePlugin : AndroidModulePlugin() {
    override fun apply(target: Project) {
        super.apply(target)

        configureAndroidApp(target.extensions.getByType(BaseAppModuleExtension::class.java))
    }

    override fun configurePlugins(pluginContainer: PluginContainer) {
        pluginContainer.apply(
            listOf(
                Plugins.androidApp,
                Plugins.hilt,
            )
        )

        super.configurePlugins(pluginContainer)
    }

    override fun configureDependencies(dependencyHandler: DependencyHandler) {
        super.configureDependencies(dependencyHandler)

        with(dependencyHandler) {
            implementation(
                mutableListOf<Any>().apply {
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

    private fun configureAndroidApp(androidExtension: BaseAppModuleExtension) {
        with(androidExtension) {
            defaultConfig {
                applicationId = AppConfig.applicationId

                versionCode = AppConfig.versionCode
                versionName = AppConfig.versionName
            }

            buildFeatures {
                compose = true
                composeOptions {
                    kotlinCompilerExtensionVersion = Dependencies.Android.Compose.version
                }
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
    }
}
