package ru.konohovalex.feature.account.presentation.auth.mapper

import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.account.domain.auth.model.AuthDataDomainModel
import ru.konohovalex.feature.account.domain.auth.model.PasswordDomainModel
import ru.konohovalex.feature.account.domain.auth.model.UserNameDomainModel
import ru.konohovalex.feature.account.presentation.auth.model.AuthDataUiModel
import javax.inject.Inject

internal class AuthDataUiModelToAuthDataDomainModelMapper
@Inject constructor() : Mapper<AuthDataUiModel, AuthDataDomainModel> {
    override fun invoke(input: AuthDataUiModel) = with(input) {
        AuthDataDomainModel(
            userNameDomainModel = UserNameDomainModel(userNameUiModel.value),
            passwordDomainModel = PasswordDomainModel(passwordUiModel.value),
        )
    }
}
