package ru.konohovalex.gradle

object Modules {
    object Core {
        private const val corePrefix = ":core"

        const val data = "$corePrefix:data"
        const val design = "$corePrefix:design"
        const val presentation = "$corePrefix:presentation"
        const val ui = "$corePrefix:ui"
        const val utils = "$corePrefix:utils"
    }

    sealed class Feature(featureName: String) {
        companion object {
            private const val featurePrefix = ":feature"

            private const val dataPostfix = ":data"
            private const val domainPostfix = ":domain"
            private const val presentationPostfix = ":presentation"

            private const val imageRecognition = ":imagerecognition"
            private const val notes = ":notes"
            private const val preferences = ":preferences"
            private const val account = ":account"
            private const val speechRecognition = ":speechrecognition"
        }

        val data = "$featurePrefix$featureName$dataPostfix"
        val domain = "$featurePrefix$featureName$domainPostfix"
        val presentation = "$featurePrefix$featureName$presentationPostfix"

        object ImageRecognition : Feature(imageRecognition)
        object Notes : Feature(notes)
        object Preferences : Feature(preferences)
        object Account : Feature(account)
        object SpeechRecognition : Feature(speechRecognition)
    }
}
