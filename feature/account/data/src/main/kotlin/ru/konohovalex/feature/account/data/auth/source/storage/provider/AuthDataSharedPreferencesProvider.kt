package ru.konohovalex.feature.account.data.auth.source.storage.provider

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import ru.konohovalex.core.data.arch.provider.Provider
import ru.konohovalex.feature.account.data.auth.source.storage.contract.AuthDataStorageContract
import javax.inject.Inject

internal class AuthDataSharedPreferencesProvider
@Inject constructor() : Provider<Context, SharedPreferences> {
    override fun provide(providerParams: Context): SharedPreferences =
        EncryptedSharedPreferences.create(
            AuthDataStorageContract.PREFERENCES_FILE_NAME,
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            providerParams,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )
}
