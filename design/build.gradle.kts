import com.owlmanners.machinenotes.gradle.AppConfig
import com.owlmanners.machinenotes.gradle.Dependencies
import com.owlmanners.machinenotes.gradle.utils.androidTestImplementation
import com.owlmanners.machinenotes.gradle.utils.implementation
import com.owlmanners.machinenotes.gradle.utils.testImplementation

plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
            add(Dependencies.Compose.ui)
            add(Dependencies.Compose.material)
        }
    )

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
