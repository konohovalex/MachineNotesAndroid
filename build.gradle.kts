buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(com.owlmanners.machinenotes.gradle.Dependencies.gradle)
        classpath(com.owlmanners.machinenotes.gradle.Dependencies.Kotlin.kotlinGradlePlugin)
        classpath(com.owlmanners.machinenotes.gradle.Dependencies.Hilt.androidGradlePlugin)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register<Delete>(name = "clean") {
    delete(rootProject.buildDir)
}
