import ru.konohovalex.gradle.Dependencies
import ru.konohovalex.gradle.utils.implementation

plugins {
    id("kotlin-module-plugin")
}

dependencies {
    implementation(
        Dependencies.Google.gson,
    )
}
