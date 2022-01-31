package ru.konohovalex.feature.account.data.profile.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import ru.konohovalex.core.data.arch.provider.Provider
import ru.konohovalex.feature.account.data.di.Qualifiers
import ru.konohovalex.feature.account.data.profile.source.provider.ProfileGsonProvider
import javax.inject.Named

@Module
@DisableInstallInCheck
internal class ProfileGsonModule {
    @Provides
    @Named(Qualifiers.PROFILE_GSON_PROVIDER)
    fun provideProfileGsonProvider(): Provider<Nothing?, Gson> = ProfileGsonProvider()

    @Provides
    @Named(Qualifiers.PROFILE_GSON)
    fun provideProfileGson(
        @Named(Qualifiers.PROFILE_GSON_PROVIDER)
        profileGsonProvider: Provider<Nothing?, Gson>,
    ): Gson = profileGsonProvider.provide(null)
}
