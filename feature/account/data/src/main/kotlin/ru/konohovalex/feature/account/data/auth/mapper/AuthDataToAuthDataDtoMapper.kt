package ru.konohovalex.feature.account.data.auth.mapper

import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.account.data.auth.model.AuthData
import ru.konohovalex.feature.account.data.auth.model.remote.AuthDataDto
import javax.inject.Inject

internal class AuthDataToAuthDataDtoMapper
@Inject constructor() : Mapper<AuthData, AuthDataDto> {
    override fun invoke(input: AuthData) = with(input) {
        AuthDataDto(
            userName = userName.value,
            password = password.value,
        )
    }
}
