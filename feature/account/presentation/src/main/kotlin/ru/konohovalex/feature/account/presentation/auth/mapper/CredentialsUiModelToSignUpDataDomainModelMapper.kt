package ru.konohovalex.feature.account.presentation.auth.mapper

import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.account.domain.auth.model.SignUpDataDomainModel
import ru.konohovalex.feature.account.domain.auth.model.CredentialsDomainModel
import ru.konohovalex.feature.account.domain.auth.model.PasswordDomainModel
import ru.konohovalex.feature.account.domain.auth.model.UserNameDomainModel
import ru.konohovalex.feature.account.presentation.auth.model.CredentialsUiModel
import javax.inject.Inject

internal class CredentialsUiModelToSignUpDataDomainModelMapper
@Inject constructor() : Mapper<CredentialsUiModel, SignUpDataDomainModel> {
    override fun invoke(input: CredentialsUiModel) = with(input) {
        SignUpDataDomainModel(
            credentialsDomainModel = CredentialsDomainModel(
                userNameDomainModel = UserNameDomainModel(userNameUiModel.value),
                passwordDomainModel = PasswordDomainModel(passwordUiModel.value),
            ),
        )
    }
}
