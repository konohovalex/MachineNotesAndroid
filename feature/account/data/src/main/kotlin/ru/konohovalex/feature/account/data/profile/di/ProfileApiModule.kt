package ru.konohovalex.feature.account.data.profile.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.converter.gson.GsonConverterFactory
import ru.konohovalex.core.data.arch.source.api.provider.ApiProvider
import ru.konohovalex.feature.account.data.profile.source.api.ProfileApi
import ru.konohovalex.feature.account.data.profile.source.api.provider.ProfileApiProvider
import ru.konohovalex.feature.account.data.profile.source.api.provider.ProfileGsonProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ProfileApiModule {
    @Provides
    fun provideProfileApiProvider(): ApiProvider<GsonConverterFactory, ProfileApi> = ProfileApiProvider()

    @Provides
    @Singleton
    fun provideProfileApi(
        profileApiProvider: ApiProvider<GsonConverterFactory, ProfileApi>,
//        profileGsonConverterFactory: GsonConverterFactory,
    ): ProfileApi = profileApiProvider.provide(
        GsonConverterFactory.create(
            ProfileGsonProvider().provide(null)
        )
    )
}
