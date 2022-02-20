package ru.konohovalex.feature.account.domain.auth.mapper

import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.account.data.auth.model.SignUpData
import ru.konohovalex.feature.account.data.auth.model.Credentials
import ru.konohovalex.feature.account.data.auth.model.Password
import ru.konohovalex.feature.account.data.auth.model.UserName
import ru.konohovalex.feature.account.domain.auth.model.SignUpDataDomainModel
import javax.inject.Inject

internal class SignUpDataDomainModelToSignUpDataMapper
@Inject constructor() : Mapper<SignUpDataDomainModel, SignUpData> {
    override fun invoke(input: SignUpDataDomainModel) = with(input) {
        credentialsDomainModel?.let {
            SignUpData(
                credentials = Credentials(
                    userName = UserName(credentialsDomainModel.userNameDomainModel.value),
                    password = Password(credentialsDomainModel.passwordDomainModel.value),
                ),
            )
        } ?: SignUpData(credentials = null)
    }
}
