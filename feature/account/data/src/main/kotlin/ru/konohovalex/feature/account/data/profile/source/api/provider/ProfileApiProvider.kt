package ru.konohovalex.feature.account.data.profile.source.api.provider

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.konohovalex.core.data.Constants
import ru.konohovalex.core.data.arch.source.api.provider.ApiProvider
import ru.konohovalex.feature.account.data.profile.source.api.ProfileApi
import javax.inject.Inject

internal class ProfileApiProvider
@Inject constructor() : ApiProvider<GsonConverterFactory, ProfileApi> {
    override fun provide(providerParams: GsonConverterFactory): ProfileApi = Retrofit.Builder()
        .baseUrl(Constants.BASE_API_URL)
        .addConverterFactory(providerParams)
        .build()
        .create(ProfileApi::class.java)
}
