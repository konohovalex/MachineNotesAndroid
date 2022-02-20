package ru.konohovalex.feature.account.data.profile.model.entity

internal data class ProfileEntity(val userName: String?) {
    companion object {
        fun empty() = ProfileEntity(null)
    }
}
