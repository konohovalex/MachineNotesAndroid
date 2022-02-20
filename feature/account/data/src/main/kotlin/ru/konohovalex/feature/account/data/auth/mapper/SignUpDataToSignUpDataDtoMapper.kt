package ru.konohovalex.feature.account.data.auth.mapper

import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.account.data.auth.model.SignUpData
import ru.konohovalex.feature.account.data.auth.model.remote.SingUpDataDto
import ru.konohovalex.feature.account.data.auth.model.remote.CredentialsDto
import javax.inject.Inject

internal class SignUpDataToSignUpDataDtoMapper
@Inject constructor() : Mapper<SignUpData, SingUpDataDto> {
    override fun invoke(input: SignUpData) = with(input) {
        credentials?.let {
            SingUpDataDto(
                credentialsDto = CredentialsDto(
                    userName = credentials.userName.value,
                    password = credentials.password.value,
                ),
            )
        } ?: SingUpDataDto(credentialsDto = null)
    }
}
