import ru.konohovalex.gradle.Modules
import ru.konohovalex.gradle.utils.implementation

plugins {
    id("android-hilted-library-module-plugin")
}

dependencies {
    implementation(
        project(Modules.Core.data),
        project(Modules.Core.utils),

        project(Modules.Feature.Account.data),

        project(Modules.Feature.Notes.domain),
    )
}
