package ru.konohovalex.feature.preferences.data.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import ru.konohovalex.core.data.arch.provider.Provider
import ru.konohovalex.feature.preferences.data.source.provider.PreferencesDataGsonProvider
import javax.inject.Named

@Module
@DisableInstallInCheck
internal class PreferencesDataGsonModule {
    @Provides
    @Named(Qualifiers.PREFERENCES_DATA_GSON_PROVIDER)
    fun providePreferencesDataGsonProvider(): Provider<Nothing?, Gson> = PreferencesDataGsonProvider()

    @Provides
    @Named(Qualifiers.PREFERENCES_DATA_GSON)
    fun providePreferencesDataGson(
        @Named(Qualifiers.PREFERENCES_DATA_GSON_PROVIDER)
        preferencesDataGsonProvider: Provider<Nothing?, Gson>,
    ): Gson = preferencesDataGsonProvider.provide(null)
}
