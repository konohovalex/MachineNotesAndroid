import ru.konohovalex.gradle.Dependencies
import ru.konohovalex.gradle.Modules
import ru.konohovalex.gradle.utils.implementation
import ru.konohovalex.gradle.utils.kapt

plugins {
    id("android-app-module-plugin")
}

dependencies {
    implementation(
        mutableListOf<Any>().apply {
            add(Dependencies.Android.coreKtx)
            add(Dependencies.Android.appCompat)
            add(Dependencies.Android.material)

            addAll(Dependencies.Android.Compose.getAllRuntimeDependencies())

            addAll(Dependencies.Room.getAllRuntimeDependencies())

            addAll(
                listOf(
                    project(Modules.Core.data),
                    project(Modules.Core.design),
                    project(Modules.Features.Api.notes),
                    project(Modules.Features.Api.preferences),
                    project(Modules.Features.Api.profile),
                    project(Modules.Features.Api.imageRecognition),
                    project(Modules.Features.Api.speechRecognition),
                )
            )
        }
    )

    kapt(
        listOf(
            Dependencies.Room.compiler,
        )
    )
}
