package ru.konohovalex.feature.account.data.profile.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import kotlinx.coroutines.CoroutineScope
import ru.konohovalex.feature.account.data.di.Qualifiers
import ru.konohovalex.feature.account.data.profile.source.storage.contract.ProfileStorageContract
import ru.konohovalex.feature.account.data.profile.source.storage.impl.ProfileStorageImpl
import javax.inject.Named
import javax.inject.Singleton

@Module
@DisableInstallInCheck
internal class ProfileStorageModule {
    @Provides
    @Singleton
    fun provideProfileStorage(
        @Named(Qualifiers.ACCOUNT_DATA_COROUTINE_SCOPE)
        accountDataCoroutineScope: CoroutineScope,
        @Named(Qualifiers.PROFILE_PREFERENCES_DATA_STORE)
        profilePreferencesDataStore: DataStore<Preferences>,
        @Named(Qualifiers.PROFILE_GSON)
        profileGson: Gson,
    ): ProfileStorageContract = ProfileStorageImpl(
        coroutineScope = accountDataCoroutineScope,
        preferencesDataStore = profilePreferencesDataStore,
        gson = profileGson,
    )
}
