import ru.konohovalex.gradle.Dependencies
import ru.konohovalex.gradle.Modules
import ru.konohovalex.gradle.utils.implementation

plugins {
    id("android-library-module-plugin")
}

dependencies {
    implementation(
        project(Modules.Core.utils),

        Dependencies.Android.Lifecycle.liveData,
    )
}
