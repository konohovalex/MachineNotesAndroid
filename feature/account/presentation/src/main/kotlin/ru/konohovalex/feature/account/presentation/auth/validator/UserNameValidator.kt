package ru.konohovalex.feature.account.presentation.auth.validator

import ru.konohovalex.core.ui.model.TextWrapper
import ru.konohovalex.core.utils.model.Validator
import ru.konohovalex.feature.account.presentation.R
import ru.konohovalex.feature.account.presentation.auth.model.UserNameUiModel

class UserNameValidator : Validator<UserNameUiModel, TextWrapper?> {
    companion object {
        private const val MIN_USER_NAME_LENGTH = 4
        private const val MAX_USER_NAME_LENGTH = 32
    }

    override fun validate(input: UserNameUiModel): TextWrapper? =
        when {
            input.value.length < MIN_USER_NAME_LENGTH -> TextWrapper.StringResource(
                resourceId = R.string.min_length_limitation,
                formatArgs = arrayOf(MIN_USER_NAME_LENGTH),
            )
            input.value.length > MAX_USER_NAME_LENGTH -> TextWrapper.StringResource(
                resourceId = R.string.max_length_limitation,
                formatArgs = arrayOf(MAX_USER_NAME_LENGTH),
            )
            else -> null
        }
}
