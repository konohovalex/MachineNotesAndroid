import ru.konohovalex.gradle.Dependencies
import ru.konohovalex.gradle.utils.implementation

plugins {
    id("android-compose-library-module-plugin")
}

dependencies {
    implementation(
        Dependencies.Android.Compose.ui,
        Dependencies.Android.Compose.material,
    )
}
