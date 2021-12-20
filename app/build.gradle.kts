import com.owlmanners.machinenotes.gradle.AppConfig
import com.owlmanners.machinenotes.gradle.Dependencies
import com.owlmanners.machinenotes.gradle.Modules
import com.owlmanners.machinenotes.gradle.SdkConfig
import com.owlmanners.machinenotes.gradle.utils.androidTestImplementation
import com.owlmanners.machinenotes.gradle.utils.implementation
import com.owlmanners.machinenotes.gradle.utils.kapt
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
    compileSdk = SdkConfig.compileSdk

    defaultConfig {
        applicationId = AppConfig.applicationId
        minSdk = SdkConfig.minSdk
        targetSdk = SdkConfig.targetSdk
        versionCode = SdkConfig.versionCode
        versionName = SdkConfig.versionName

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
            kotlinCompilerExtensionVersion = Dependencies.Android.Compose.version
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
            add(Dependencies.Android.coreKtx)
            add(Dependencies.Android.appCompat)
            add(Dependencies.Android.material)
            addAll(Dependencies.Android.Compose.getAllRuntimeDependencies())

            addAll(Dependencies.Hilt.getAllRuntimeDependencies())

            addAll(Dependencies.Room.getAllRuntimeDependencies())
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
