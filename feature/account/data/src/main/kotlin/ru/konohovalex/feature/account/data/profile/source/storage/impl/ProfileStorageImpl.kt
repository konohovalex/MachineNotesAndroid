package ru.konohovalex.feature.account.data.profile.source.storage.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.konohovalex.core.utils.extension.fromJson
import ru.konohovalex.feature.account.data.profile.model.entity.ProfileEntity
import ru.konohovalex.feature.account.data.profile.source.storage.contract.ProfileStorageContract
import javax.inject.Inject

internal class ProfileStorageImpl
@Inject constructor(
    private val coroutineScope: CoroutineScope,
    private val preferencesDataStore: DataStore<Preferences>,
    private val gson: Gson,
) : ProfileStorageContract {
    companion object {
        private val KEY_PROFILE = stringPreferencesKey(name = "key_profile")
    }

    private var profileStateFlow: StateFlow<ProfileEntity?>? = null

    override suspend fun observeProfile(): Flow<ProfileEntity?> =
        getProfileStateFlow()

    override suspend fun getCurrentProfile(): ProfileEntity? =
        getProfileStateFlow().value

    override suspend fun updateProfile(profileEntity: ProfileEntity): ProfileEntity =
        preferencesDataStore
            .edit {
                it[KEY_PROFILE] = gson.toJson(profileEntity)
            }
            .let {
                it[KEY_PROFILE]?.let(::mapJsonToProfileEntity)
                    ?: dataStoreUpdateError(profileEntity)
            }

    private suspend fun getProfileStateFlow(): StateFlow<ProfileEntity?> =
        profileStateFlow ?: initProfileStateFlow()

    private suspend fun initProfileStateFlow(): StateFlow<ProfileEntity?> =
        preferencesDataStore
            .data
            .map {
                it[KEY_PROFILE]?.let(::mapJsonToProfileEntity)
            }
            .stateIn(coroutineScope)
            .also { profileStateFlow = it }

    private fun mapJsonToProfileEntity(profileJson: String): ProfileEntity =
        gson.fromJson<ProfileEntity>(profileJson)

    private fun dataStoreUpdateError(profileEntity: ProfileEntity): Nothing =
        throw IllegalStateException(
            "Expecting ${profileEntity.javaClass.simpleName} with name: [${profileEntity.name}], but got null"
        )
}
