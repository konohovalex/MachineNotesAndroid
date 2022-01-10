package ru.konohovalex.gradle.plugins.android

import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.PluginContainer
import ru.konohovalex.gradle.Dependencies
import ru.konohovalex.gradle.SdkConfig
import ru.konohovalex.gradle.plugins.KotlinModulePlugin
import ru.konohovalex.gradle.plugins.Plugins
import ru.konohovalex.gradle.utils.androidTestImplementation
import ru.konohovalex.gradle.utils.apply
import ru.konohovalex.gradle.utils.implementation

abstract class AndroidModulePlugin : KotlinModulePlugin() {
    override fun apply(target: Project) {
        super.apply(target)

        configureAndroid(target.extensions.getByType(BaseExtension::class.java))
    }

    override fun configurePlugins(pluginContainer: PluginContainer) {
        pluginContainer.apply(
            Plugins.kotlinAndroid,
            Plugins.kapt,
            Plugins.ktx,
        )
    }

    override fun configureDependencies(dependencyHandler: DependencyHandler) {
        super.configureDependencies(dependencyHandler)

        with(dependencyHandler) {
            implementation(
                Dependencies.Android.coreKtx,

                Dependencies.Kotlin.Coroutines.android,
            )

            androidTestImplementation(
                Dependencies.Testing.jUnitAndroidExt,
                Dependencies.Testing.espresso,
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

                testInstrumentationRunner = Dependencies.Testing.jUnitTestRunnerAndroid
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
            }
        }
    }
}
