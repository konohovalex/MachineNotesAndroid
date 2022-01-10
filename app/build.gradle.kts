import ru.konohovalex.gradle.Dependencies
import ru.konohovalex.gradle.Modules
import ru.konohovalex.gradle.utils.implementation

plugins {
    id("android-app-module-plugin")
}

dependencies {
    implementation(
        project(Modules.Core.data),
        project(Modules.Core.design),
        project(Modules.Core.utils),

        project(Modules.Feature.Notes.presentation),

        Dependencies.Android.coreKtx,
        Dependencies.Android.appCompat,
        Dependencies.Android.material,

        Dependencies.Android.Compose.getAllRuntimeDependencies(),
    )
}
