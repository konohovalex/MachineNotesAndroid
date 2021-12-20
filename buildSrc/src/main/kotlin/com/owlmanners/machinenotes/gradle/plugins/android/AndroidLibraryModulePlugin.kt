package com.owlmanners.machinenotes.gradle.plugins.android

import com.android.build.gradle.LibraryExtension
import com.owlmanners.machinenotes.gradle.plugins.Plugins
import com.owlmanners.machinenotes.gradle.utils.apply
import org.gradle.api.Project
import org.gradle.api.plugins.PluginContainer

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
                    kotlinCompilerExtensionVersion = com.owlmanners.machinenotes.gradle.Dependencies.Android.Compose.version
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
