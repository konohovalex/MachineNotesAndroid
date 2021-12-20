import com.owlmanners.machinenotes.gradle.Dependencies
import com.owlmanners.machinenotes.gradle.Modules
import com.owlmanners.machinenotes.gradle.utils.implementation
import com.owlmanners.machinenotes.gradle.utils.kapt

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
                    project(Modules.core),
                    project(Modules.design),
                    project(Modules.Features.notes),
                    project(Modules.Features.preferences),
                    project(Modules.Features.profile),
                    project(Modules.Features.imageRecognition),
                    project(Modules.Features.speechRecognition),
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
