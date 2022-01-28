package ru.konohovalex.feature.account.data.profile.di

// tbd fix
/*import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.converter.gson.GsonConverterFactory
import ru.konohovalex.core.data.arch.provider.Provider
import ru.konohovalex.feature.account.data.source.api.provider.ProfileGsonProvider

@Module
@InstallIn(SingletonComponent::class)
internal class ProfileGsonModule {
    @Provides
    fun provideProfileGsonProvider(): Provider<Nothing?, Gson> = ProfileGsonProvider()

    @Provides
    fun provideProfileGson(
        profileGsonProvider: Provider<Nothing?, Gson>,
    ): Gson = profileGsonProvider.provide(null)

    @Provides
    fun provideProfileGsonConverterFactory(
        profileGson: Gson,
    ): GsonConverterFactory = GsonConverterFactory.create(profileGson)
}*/
