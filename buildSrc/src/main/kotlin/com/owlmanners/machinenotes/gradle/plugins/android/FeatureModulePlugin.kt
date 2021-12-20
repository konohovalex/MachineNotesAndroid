package com.owlmanners.machinenotes.gradle.plugins.android

import com.owlmanners.machinenotes.gradle.Dependencies
import com.owlmanners.machinenotes.gradle.utils.implementation
import com.owlmanners.machinenotes.gradle.utils.kapt
import org.gradle.api.artifacts.dsl.DependencyHandler

class FeatureModulePlugin : AndroidLibraryModulePlugin() {
    override fun configureDependencies(dependencyHandler: DependencyHandler) {
        super.configureDependencies(dependencyHandler)

        with(dependencyHandler) {
            implementation(
                mutableListOf<String>().apply {
                    listOf(
                        Dependencies.Android.appCompat,
                        Dependencies.Android.material,
                    )

                    addAll(Dependencies.Android.Compose.getAllRuntimeDependencies())

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
}
