package com.owlmanners.machinenotes.gradle

object Modules {
    const val core = ":core"

    const val design = ":design"

    object Features {
        private const val featuresPrefix = ":feature"
        const val notes = "$featuresPrefix:notes"
        const val preferences = "$featuresPrefix:preferences"
        const val profile = "$featuresPrefix:profile"
        const val imageRecognition = "$featuresPrefix:imagerecognition"
        const val speechRecognition = "$featuresPrefix:speechrecognition"
    }
}
