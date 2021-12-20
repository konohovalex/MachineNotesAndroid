package com.owlmanners.machinenotes.gradle

object Dependencies {
    const val gradle = "com.android.tools.build:gradle:7.0.4"

    object Kotlin {
        private const val version = "1.5.31"

        const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib:$version"

        const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"

        object Coroutines {
            private const val version = "1.5.2"

            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"

            const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
        }
    }

    object Android {
        const val coreKtx = "androidx.core:core-ktx:1.7.0"
        const val appCompat = "androidx.appcompat:appcompat:1.4.0"
        const val material = "com.google.android.material:material:1.4.0"

        object Compose {
            const val version = "1.0.5"

            const val ui = "androidx.compose.ui:ui:$version"
            const val material = "androidx.compose.material:material:$version"
            const val uiTooling = "androidx.compose.ui:ui-tooling:$version"
            const val animation = "androidx.compose.animation:animation:$version"
            const val activity = "androidx.activity:activity-compose:1.4.0"
            const val navigation = "androidx.navigation:navigation-compose:2.4.0-rc01"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:2.4.0"

            fun getAllRuntimeDependencies() = listOf(
                ui,
                material,
                uiTooling,
                animation,
                activity,
                navigation,
                viewModel,
            )
        }
    }

    object Hilt {
        private const val version = "2.40.1"

        private const val core = "com.google.dagger:hilt-android:$version"
        private const val navigationCompose = "androidx.hilt:hilt-navigation-compose:1.0.0-rc01"

        const val androidGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
        const val compiler = "com.google.dagger:hilt-compiler:$version"

        fun getAllRuntimeDependencies() = listOf(
            core,
            navigationCompose,
        )
    }

    object Room {
        private const val version = "2.4.0"

        private const val ktx = "androidx.room:room-ktx:$version"
        private const val runtime = "androidx.room:room-runtime:$version"
        private const val paging = "androidx.room:room-paging:$version"

        const val compiler = "androidx.room:room-compiler:$version"

        fun getAllRuntimeDependencies() = listOf(
            ktx,
            runtime,
            paging,
        )
    }

    object Testing {
        const val jUnit = "junit:junit:4.13.2"
        const val jUnitAndroidExt = "androidx.test.ext:junit:1.1.3"
        const val jUnitTestRunnerAndroid = "androidx.test.runner.AndroidJUnitRunner"
        const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
    }
}
