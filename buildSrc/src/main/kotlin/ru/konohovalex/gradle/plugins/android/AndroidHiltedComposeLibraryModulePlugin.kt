package ru.konohovalex.gradle.plugins.android

import com.android.build.gradle.LibraryExtension
import ru.konohovalex.gradle.plugins.configuration.ComposeConfigurator

open class AndroidHiltedComposeLibraryModulePlugin : AndroidHiltedLibraryModulePlugin() {
    private val composeConfigurator = ComposeConfigurator()

    override fun configureAndroidLibrary(libraryExtension: LibraryExtension) {
        super.configureAndroidLibrary(libraryExtension)

        composeConfigurator.configureCompose(libraryExtension)
    }
}
