import ru.konohovalex.gradle.Dependencies
import ru.konohovalex.gradle.Modules
import ru.konohovalex.gradle.utils.implementation

plugins {
    id("android-hilted-library-module-plugin")
}

dependencies {
    implementation(
        project(Modules.Core.data),
        project(Modules.Core.utils),

        Dependencies.Android.dataStorePreferences,
        Dependencies.Android.securityCrypto,

        Dependencies.Retrofit.getAllRuntimeDependencies(),
    )
}
