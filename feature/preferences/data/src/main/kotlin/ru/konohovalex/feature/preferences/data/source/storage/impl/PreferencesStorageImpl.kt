package ru.konohovalex.feature.preferences.data.source.storage.impl

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
import ru.konohovalex.feature.preferences.data.model.entity.PreferencesEntity
import ru.konohovalex.feature.preferences.data.source.storage.contract.PreferencesStorageContract
import javax.inject.Inject

internal class PreferencesStorageImpl
@Inject constructor(
    private val coroutineScope: CoroutineScope,
    private val preferencesDataStore: DataStore<Preferences>,
    private val gson: Gson,
) : PreferencesStorageContract {
    companion object {
        private val KEY_PREFERENCES = stringPreferencesKey(name = "key_preferences")
    }

    private var preferencesStateFlow: StateFlow<PreferencesEntity>? = null

    override suspend fun observePreferences(): Flow<PreferencesEntity> =
        getPreferencesStateFlow()

    override suspend fun getCurrentPreferences(): PreferencesEntity =
        getPreferencesStateFlow().value

    override suspend fun updatePreferences(preferencesEntity: PreferencesEntity): PreferencesEntity =
        preferencesDataStore
            .edit {
                it[KEY_PREFERENCES] = gson.toJson(preferencesEntity)
            }
            .let {
                it[KEY_PREFERENCES]?.let(::mapJsonToPreferencesEntity)
                    ?: dataStoreUpdateError(preferencesEntity)
            }

    private suspend fun getPreferencesStateFlow(): StateFlow<PreferencesEntity> =
        preferencesStateFlow ?: initPreferencesStateFlow()

    private suspend fun initPreferencesStateFlow(): StateFlow<PreferencesEntity> =
        preferencesDataStore
            .data
            .map {
                it[KEY_PREFERENCES]?.let(::mapJsonToPreferencesEntity)
                    ?: PreferencesEntity.default()
            }
            .stateIn(coroutineScope)
            .also { preferencesStateFlow = it }

    private fun mapJsonToPreferencesEntity(preferencesJson: String) =
        gson.fromJson<PreferencesEntity>(preferencesJson)

    private fun dataStoreUpdateError(preferencesEntity: PreferencesEntity): Nothing =
        throw IllegalStateException(
            "Expecting [${preferencesEntity}], but got null"
        )
}
