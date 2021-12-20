package ru.konohovalex.gradle.plugins.android

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Project
import org.gradle.api.plugins.PluginContainer
import ru.konohovalex.gradle.Dependencies
import ru.konohovalex.gradle.plugins.Plugins
import ru.konohovalex.gradle.utils.apply

open class AndroidLibraryModulePlugin : AndroidModulePlugin() {
    override fun apply(target: Project) {
        super.apply(target)

        configureAndroidLibrary(target.extensions.getByType(LibraryExtension::class.java))
    }

    override fun configurePlugins(pluginContainer: PluginContainer) {
        pluginContainer.apply(
            listOf(
                Plugins.androidLibrary,
            )
        )

        super.configurePlugins(pluginContainer)
    }

    private fun configureAndroidLibrary(libraryExtension: LibraryExtension) {
        with(libraryExtension) {
            defaultConfig {
                consumerProguardFiles("consumer-rules.pro")
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
