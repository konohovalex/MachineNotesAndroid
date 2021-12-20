package com.owlmanners.machinenotes.gradle.plugins.android

import com.android.build.gradle.BaseExtension
import com.owlmanners.machinenotes.gradle.Dependencies
import com.owlmanners.machinenotes.gradle.SdkConfig
import com.owlmanners.machinenotes.gradle.plugins.KotlinModulePlugin
import com.owlmanners.machinenotes.gradle.plugins.Plugins
import com.owlmanners.machinenotes.gradle.utils.androidTestImplementation
import com.owlmanners.machinenotes.gradle.utils.apply
import com.owlmanners.machinenotes.gradle.utils.implementation
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.PluginContainer
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

abstract class AndroidModulePlugin : KotlinModulePlugin() {
    override fun apply(target: Project) {
        super.apply(target)

        configureAndroid(target.extensions.getByType(BaseExtension::class.java))

        target.tasks.withType<KotlinCompile>() {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_1_8.toString()
            }
        }
    }

    override fun configurePlugins(pluginContainer: PluginContainer) {
        super.configurePlugins(pluginContainer)

        pluginContainer.apply(
            listOf(
                Plugins.kotlinAndroid,
                Plugins.ktx,
            )
        )
    }

    override fun configureDependencies(dependencyHandler: DependencyHandler) {
        super.configureDependencies(dependencyHandler)

        with(dependencyHandler) {
            implementation(
                listOf(
                    Dependencies.Android.coreKtx,

                    Dependencies.Kotlin.Coroutines.android,
                )
            )

            androidTestImplementation(
                listOf(
                    Dependencies.Testing.jUnitAndroidExt,
                )
            )
        }
    }

    open fun configureAndroid(androidExtension: BaseExtension) {
        with(androidExtension) {
            lintOptions.isAbortOnError = false

            compileSdkVersion(SdkConfig.compileSdk)

            defaultConfig {
                minSdk = SdkConfig.minSdk
                targetSdk = SdkConfig.targetSdk

                versionCode = SdkConfig.versionCode
                versionName = SdkConfig.versionName

                testInstrumentationRunner = Dependencies.Testing.jUnitTestRunnerAndroid
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
            }
        }
    }
}
