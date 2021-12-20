import ru.konohovalex.gradle.Modules
import ru.konohovalex.gradle.utils.implementation

plugins {
    id("feature-module-plugin")
}

dependencies {
    implementation(
        listOf(
            project(Modules.Core.data),
            project(Modules.Core.design),

            project(Modules.Features.Api.navigation),
        )
    )
}
