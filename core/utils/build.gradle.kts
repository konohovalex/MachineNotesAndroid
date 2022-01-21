import ru.konohovalex.gradle.Dependencies
import ru.konohovalex.gradle.Modules
import ru.konohovalex.gradle.utils.implementation

plugins {
    id("kotlin-module-plugin")
}

dependencies {
    implementation(
        project(Modules.Core.data),

        Dependencies.Google.gson,
    )
}
