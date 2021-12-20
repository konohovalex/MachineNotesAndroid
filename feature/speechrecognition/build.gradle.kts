import com.owlmanners.machinenotes.gradle.SdkConfig
import com.owlmanners.machinenotes.gradle.Dependencies
import com.owlmanners.machinenotes.gradle.Modules
import com.owlmanners.machinenotes.gradle.utils.androidTestImplementation
import com.owlmanners.machinenotes.gradle.utils.testImplementation

plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdk = SdkConfig.compileSdk

    defaultConfig {
        minSdk = SdkConfig.minSdk
        targetSdk = SdkConfig.targetSdk

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
    implementation(project(Modules.core))
    implementation(project(Modules.design))

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
