package ru.konohovalex.feature.preferences.data.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import ru.konohovalex.core.data.arch.provider.Provider
import ru.konohovalex.feature.preferences.data.source.provider.PreferencesGsonProvider
import javax.inject.Named

@Module
@DisableInstallInCheck
internal class PreferencesGsonModule {
    @Provides
    @Named(Qualifiers.PREFERENCES_GSON_PROVIDER)
    fun providePreferencesGsonProvider(): Provider<Nothing?, Gson> = PreferencesGsonProvider()

    @Provides
    @Named(Qualifiers.PREFERENCES_GSON)
    fun providePreferencesGson(
        @Named(Qualifiers.PREFERENCES_GSON_PROVIDER)
        preferencesGsonProvider: Provider<Nothing?, Gson>,
    ): Gson = preferencesGsonProvider.provide(null)
}
