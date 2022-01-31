package ru.konohovalex.feature.account.data.source.api.provider

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.konohovalex.core.data.Constants
import ru.konohovalex.core.data.arch.source.api.provider.ApiProvider
import ru.konohovalex.feature.account.data.source.api.AccountApi
import javax.inject.Inject

internal class AccountApiProvider
@Inject constructor() : ApiProvider<GsonConverterFactory, AccountApi> {
    override fun provide(providerParams: GsonConverterFactory): AccountApi = Retrofit.Builder()
        .baseUrl(Constants.BASE_API_URL)
        .addConverterFactory(providerParams)
        .build()
        .create(AccountApi::class.java)
}
