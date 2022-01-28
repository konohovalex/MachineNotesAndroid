package ru.konohovalex.feature.account.domain.auth.mapper

import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.account.data.auth.model.AuthData
import ru.konohovalex.feature.account.data.auth.model.Password
import ru.konohovalex.feature.account.data.auth.model.UserName
import ru.konohovalex.feature.account.domain.auth.model.AuthDataDomainModel
import javax.inject.Inject

internal class AuthDataDomainModelToAuthDataMapper
@Inject constructor() : Mapper<AuthDataDomainModel, AuthData> {
    override fun invoke(input: AuthDataDomainModel) = with(input) {
        AuthData(
            userName = UserName(userNameDomainModel.value),
            password = Password(passwordDomainModel.value),
        )
    }
}
