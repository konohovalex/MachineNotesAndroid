import com.owlmanners.machinenotes.gradle.Dependencies
import com.owlmanners.machinenotes.gradle.utils.implementation

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
