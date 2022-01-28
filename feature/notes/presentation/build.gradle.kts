import ru.konohovalex.gradle.Dependencies
import ru.konohovalex.gradle.Modules
import ru.konohovalex.gradle.utils.implementation

plugins {
    id("android-hilted-compose-library-module-plugin")
}

dependencies {
    implementation(
        project(Modules.Core.design),
        project(Modules.Core.presentation),
        project(Modules.Core.ui),
        project(Modules.Core.utils),

        project(Modules.Feature.Notes.domain),

        Dependencies.Hilt.navigationCompose,

        Dependencies.Android.Lifecycle.viewModelCompose,

        Dependencies.Android.Compose.getAllRuntimeDependencies(),
    )
}
