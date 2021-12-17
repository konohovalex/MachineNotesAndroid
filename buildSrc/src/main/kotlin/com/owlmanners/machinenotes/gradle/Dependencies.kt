package com.owlmanners.machinenotes.gradle

object Dependencies {
    private const val kotlinVersion = "1.6.0"
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"

    const val gradle = "com.android.tools.build:gradle:7.0.4"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"

    const val coreKtx = "androidx.core:core-ktx:1.7.0"
    const val appCompat = "androidx.appcompat:appcompat:1.4.0"
    const val material = "com.google.android.material:material:1.4.0"

    object Compose {
        private const val composeVersion = "1.0.5"
        const val ui = "androidx.compose.ui:ui:$composeVersion"
        const val material = "androidx.compose.material:material:$composeVersion"
        const val uiTooling = "androidx.compose.ui:ui-tooling:$composeVersion"
        const val animation = "androidx.compose.animation:animation:$composeVersion"
        const val activity = "androidx.activity:activity-compose:1.4.0"
        const val navigation = "androidx.navigation:navigation-compose:2.4.0-beta02"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:2.4.0"

        fun getAll() = listOf(
            ui,
            material,
            uiTooling,
            animation,
            activity,
            navigation,
            viewModel,
        )
    }

    object Hilt {
        const val version = "2.40.5"

        const val androidGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$version"

        const val core = "com.google.dagger:hilt-android:$version"
        const val navigationCompose = "androidx.hilt:hilt-navigation-compose:1.0.0-beta01"

        const val compiler = "com.google.dagger:hilt-compiler:$version"

        fun getAll() = listOf(
            core,
            navigationCompose,
        )
    }

    object Room {
        private const val roomVersion = "2.4.0"

        const val ktx = "androidx.room:room-ktx:$roomVersion"
        const val runtime = "androidx.room:room-runtime:$roomVersion"
        const val paging = "androidx.room:room-paging:$roomVersion"

        const val compiler = "androidx.room:room-compiler:$roomVersion"

        fun getAll() = listOf(
            ktx,
            runtime,
            paging,
        )
    }

    object Testing {
        const val jUnit = "junit:junit:4.13.2"
        const val jUnitAndroidExt = "androidx.test.ext:junit:1.1.3"
        const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
    }
}
