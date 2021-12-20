buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(ru.konohovalex.gradle.Dependencies.gradle)
        classpath(ru.konohovalex.gradle.Dependencies.Kotlin.kotlinGradlePlugin)
        classpath(ru.konohovalex.gradle.Dependencies.Hilt.androidGradlePlugin)
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
