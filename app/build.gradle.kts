import com.owlmanners.machinenotes.gradle.AppConfig
import com.owlmanners.machinenotes.gradle.Dependencies
import com.owlmanners.machinenotes.gradle.Modules
import com.owlmanners.machinenotes.gradle.utils.androidTestImplementation
import com.owlmanners.machinenotes.gradle.utils.kapt
import com.owlmanners.machinenotes.gradle.utils.implementation
import com.owlmanners.machinenotes.gradle.utils.testImplementation

plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
}

kapt {
    correctErrorTypes = true
}

hilt {
    enableAggregatingTask = true
}

android {
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = AppConfig.packageName
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    buildFeatures {
        composeOptions {
            compose = true
            kotlinCompilerExtensionVersion = AppConfig.kotlinCompilerExtensionVersion
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(
        mutableListOf<String>().apply {
            add(Dependencies.coreKtx)
            add(Dependencies.appCompat)
            add(Dependencies.material)

            addAll(Dependencies.Compose.getAll())

            addAll(Dependencies.Hilt.getAll())

            addAll(Dependencies.Room.getAll())
        }
    )

    kapt(
        listOf(
            Dependencies.Hilt.compiler,
            Dependencies.Room.compiler,
        )
    )

    implementation(project(Modules.core))

    implementation(project(Modules.design))

    implementation(project(Modules.Features.notes))
    implementation(project(Modules.Features.preferences))
    implementation(project(Modules.Features.profile))
    implementation(project(Modules.Features.imageRecognition))
    implementation(project(Modules.Features.speechRecognition))

    testImplementation(
        listOf(
            Dependencies.Testing.jUnit,
        )
    )

    androidTestImplementation(
        listOf(
            Dependencies.Testing.jUnitAndroidExt,
            Dependencies.Testing.espresso,
        )
    )
}
