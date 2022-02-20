package ru.konohovalex.feature.account.data.source.network.api.provider

import retrofit2.Retrofit
import ru.konohovalex.core.data.Constants
import ru.konohovalex.core.data.arch.source.network.api.provider.ApiProvider
import ru.konohovalex.feature.account.data.source.network.api.AccountApi
import javax.inject.Inject

internal class AccountApiProvider
@Inject constructor() : ApiProvider<AccountApiProviderParams, AccountApi> {
    override fun provide(providerParams: AccountApiProviderParams): AccountApi = Retrofit.Builder()
        .baseUrl(Constants.BASE_API_URL)
        .client(providerParams.okHttpClient)
        .addConverterFactory(providerParams.gsonConverterFactory)
        .build()
        .create(AccountApi::class.java)
}
