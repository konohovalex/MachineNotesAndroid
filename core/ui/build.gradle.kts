import ru.konohovalex.gradle.Dependencies
import ru.konohovalex.gradle.Modules
import ru.konohovalex.gradle.utils.implementation

plugins {
    id("android-compose-library-module-plugin")
}

dependencies {
    implementation(
        project(Modules.Core.design),

        Dependencies.Android.Compose.ui,
        Dependencies.Android.Compose.material,
        Dependencies.Android.Compose.uiTooling,
        Dependencies.Android.Compose.navigation,
        // tbd
        // This dependency should not be here, but for some weird reasons, Compose Preview needs it to just be rendered
        Dependencies.Android.Compose.activity,
    )
}
