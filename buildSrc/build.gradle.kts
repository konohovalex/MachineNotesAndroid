plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        "kotlin-module-plugin".let {
            register(it) {
                id = it
                implementationClass = "ru.konohovalex.gradle.plugins.KotlinModulePlugin"
            }
        }

        "android-library-module-plugin".let {
            register(it) {
                id = it
                implementationClass = "ru.konohovalex.gradle.plugins.android.AndroidLibraryModulePlugin"
            }
        }

        "android-hilted-library-module-plugin".let {
            register(it) {
                id = it
                implementationClass = "ru.konohovalex.gradle.plugins.android.AndroidHiltedLibraryModulePlugin"
            }
        }

        "android-compose-library-module-plugin".let {
            register(it) {
                id = it
                implementationClass = "ru.konohovalex.gradle.plugins.android.AndroidComposeLibraryModulePlugin"
            }
        }

        "android-hilted-compose-library-module-plugin".let {
            register(it) {
                id = it
                implementationClass = "ru.konohovalex.gradle.plugins.android.AndroidHiltedComposeLibraryModulePlugin"
            }
        }

        "android-app-module-plugin".let {
            register(it) {
                id = it
                implementationClass = "ru.konohovalex.gradle.plugins.android.AndroidAppModulePlugin"
            }
        }
    }
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation("com.android.tools.build:gradle:7.0.4")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
}
