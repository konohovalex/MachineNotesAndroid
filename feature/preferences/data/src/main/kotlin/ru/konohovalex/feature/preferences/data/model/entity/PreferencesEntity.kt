package ru.konohovalex.feature.preferences.data.model.entity

data class PreferencesEntity(
    val languageEntity: LanguageEntity,
    val themeModeEntity: ThemeModeEntity,
) {
    companion object {
        fun default() = PreferencesEntity(
            LanguageEntity.ENG,
            ThemeModeEntity.SYSTEM,
        )
    }
}
