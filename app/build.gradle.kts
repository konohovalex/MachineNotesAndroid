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
        project(Modules.Core.presentation),
        project(Modules.Core.ui),
        project(Modules.Core.utils),

        project(Modules.Feature.Preferences.domain),

        project(Modules.Feature.Notes.presentation),
        project(Modules.Feature.Preferences.presentation),
        project(Modules.Feature.Account.presentation),

        Dependencies.Android.coreKtx,
        Dependencies.Android.appCompat,
        Dependencies.Android.material,
        Dependencies.Android.splashScreen,
        Dependencies.Android.dataStorePreferences,

        Dependencies.Android.Lifecycle.viewModelCompose,

        Dependencies.Android.Compose.getAllRuntimeDependencies(),

        Dependencies.Hilt.navigationCompose,
    )
}
