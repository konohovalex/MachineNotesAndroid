package ru.konohovalex.gradle.plugins.configuration

import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import ru.konohovalex.gradle.Dependencies

class ComposeConfigurator {
    fun configureCompose(libraryExtension: LibraryExtension) {
        with(libraryExtension) {
            buildFeatures {
                compose = true
                composeOptions {
                    kotlinCompilerExtensionVersion = Dependencies.Android.Compose.version
                }
            }
        }
    }

    fun configureCompose(baseAppModuleExtension: BaseAppModuleExtension) {
        with(baseAppModuleExtension) {
            buildFeatures {
                compose = true
                composeOptions {
                    kotlinCompilerExtensionVersion = Dependencies.Android.Compose.version
                }
            }
        }
    }
}
