import com.owlmanners.machinenotes.gradle.Modules
import com.owlmanners.machinenotes.gradle.utils.implementation

plugins {
    id("feature-module-plugin")
}

dependencies {
    implementation(
        listOf(
            project(Modules.core),
            project(Modules.design),
        )
    )
}
