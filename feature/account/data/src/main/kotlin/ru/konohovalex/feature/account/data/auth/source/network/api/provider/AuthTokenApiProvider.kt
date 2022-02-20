package ru.konohovalex.feature.account.data.auth.source.network.api.provider

import retrofit2.Retrofit
import ru.konohovalex.core.data.Constants
import ru.konohovalex.core.data.arch.source.network.api.provider.ApiProvider
import ru.konohovalex.feature.account.data.auth.source.network.api.AuthTokenApi
import javax.inject.Inject

internal class AuthTokenApiProvider
@Inject constructor() : ApiProvider<AuthTokenApiProviderParams, AuthTokenApi> {
    override fun provide(providerParams: AuthTokenApiProviderParams): AuthTokenApi = Retrofit.Builder()
        .baseUrl(Constants.BASE_API_URL)
        .client(providerParams.okHttpClient)
        .addConverterFactory(providerParams.gsonConverterFactory)
        .build()
        .create(AuthTokenApi::class.java)
}
