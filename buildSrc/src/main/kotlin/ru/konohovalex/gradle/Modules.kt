package ru.konohovalex.gradle

object Modules {
    object Core {
        private const val corePrefix = ":core"

        const val data = "$corePrefix:data"
        const val design = "$corePrefix:design"
    }

    object Features {
        private const val featuresPrefix = ":feature"

        object Api {
            private const val apiPrefix = ":api"

            const val imageRecognition = "$featuresPrefix$apiPrefix:imagerecognition"
            const val navigation = "$featuresPrefix$apiPrefix:navigation"
            const val notes = "$featuresPrefix$apiPrefix:notes"
            const val preferences = "$featuresPrefix$apiPrefix:preferences"
            const val profile = "$featuresPrefix$apiPrefix:profile"
            const val speechRecognition = "$featuresPrefix$apiPrefix:speechrecognition"
        }

        object Impl {
            private const val implPrefix = ":impl"

            const val imageRecognition = "$featuresPrefix$implPrefix:imagerecognition"
            const val navigation = "$featuresPrefix$implPrefix:navigation"
            const val notes = "$featuresPrefix$implPrefix:notes"
            const val preferences = "$featuresPrefix$implPrefix:preferences"
            const val profile = "$featuresPrefix$implPrefix:profile"
            const val speechRecognition = "$featuresPrefix$implPrefix:speechrecognition"
        }
    }
}
