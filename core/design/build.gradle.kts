import ru.konohovalex.gradle.Dependencies
import ru.konohovalex.gradle.utils.implementation

plugins {
    id("android-library-module-plugin")
}

dependencies {
    implementation(
        mutableListOf<String>().apply {
            add(Dependencies.Android.Compose.ui)
            add(Dependencies.Android.Compose.material)
        }
    )
}
