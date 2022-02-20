package ru.konohovalex.gradle

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
        const val splashScreen = "androidx.core:core-splashscreen:1.0.0-beta01"
        const val dataStorePreferences = "androidx.datastore:datastore-preferences:1.0.0"
        const val securityCrypto = "androidx.security:security-crypto:1.0.0"

        object Lifecycle {
            private const val version = "2.4.0"

            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val viewModelSavedState = "androidx.lifecycle:lifecycle-viewmodel-savedstate:$version"
            const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$version"
            const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val service = "androidx.lifecycle:lifecycle-service:$version"
            const val process = "androidx.lifecycle:lifecycle-process:$version"
        }

        object Compose {
            const val version = "1.0.5"

            const val ui = "androidx.compose.ui:ui:$version"
            const val material = "androidx.compose.material:material:$version"
            const val uiTooling = "androidx.compose.ui:ui-tooling:$version"
            const val animation = "androidx.compose.animation:animation:$version"
            const val activity = "androidx.activity:activity-compose:1.4.0"
            const val navigation = "androidx.navigation:navigation-compose:2.4.0-rc01"
            const val livedata = "androidx.compose.runtime:runtime-livedata:$version"
            // TODO("grab necessary from https://mvnrepository.com/artifact/com.google.accompanist")

            fun getAllRuntimeDependencies() = listOf(
                ui,
                material,
                uiTooling,
                animation,
                activity,
                navigation,
                livedata,
            )
        }
    }

    object Google {
        // version, that Retrofit2 depends on
        const val gson = "com.google.code.gson:gson:2.8.5"
    }

    object Dagger {
        private const val version = "2.40.1"

        const val core = "com.google.dagger:dagger:$version"

        const val compiler = "com.google.dagger:dagger-compiler:$version"
    }

    object Hilt {
        private const val version = "2.40.1"

        const val core = "com.google.dagger:hilt-android:$version"
        const val navigationCompose = "androidx.hilt:hilt-navigation-compose:1.0.0-rc01"

        const val compiler = "com.google.dagger:hilt-compiler:$version"

        const val androidGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
    }

    object Room {
        private const val version = "2.4.0"

        const val runtime = "androidx.room:room-runtime:$version"
        const val ktx = "androidx.room:room-ktx:$version"
        const val paging = "androidx.room:room-paging:$version"

        const val compiler = "androidx.room:room-compiler:$version"

        fun getAllRuntimeDependencies() = listOf(
            ktx,
            runtime,
            paging,
        )
    }

    object OkHttp {
        private const val version = "4.9.3"

        const val core = "com.squareup.okhttp3:$version"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$version"

        fun getAllRuntimeDependencies() = listOf(
            core,
            loggingInterceptor,
        )
    }

    object Retrofit {
        // Always check, that version of Google.gson dependency is the same, as in Retrofit2 dependencies
        private const val version = "2.9.0"

        const val core = "com.squareup.retrofit2:retrofit:$version"
        const val gsonConverter = "com.squareup.retrofit2:converter-gson:$version"

        fun getAllRuntimeDependencies() = listOf(
            core,
            gsonConverter,
        )
    }

    object Testing {
        const val jUnit = "junit:junit:4.13.2"
        const val jUnitAndroidExt = "androidx.test.ext:junit:1.1.3"
        const val jUnitTestRunnerAndroid = "androidx.test.runner.AndroidJUnitRunner"
        const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
    }
}
