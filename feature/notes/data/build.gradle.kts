import ru.konohovalex.gradle.Dependencies
import ru.konohovalex.gradle.Modules
import ru.konohovalex.gradle.utils.implementation
import ru.konohovalex.gradle.utils.kapt

plugins {
    id("android-hilted-library-module-plugin")
}

dependencies {
    implementation(
        project(Modules.Core.data),
        project(Modules.Core.utils),

        Dependencies.Retrofit.getAllRuntimeDependencies(),

        Dependencies.Room.getAllRuntimeDependencies(),
    )

    kapt(
        Dependencies.Room.compiler,
    )
}
