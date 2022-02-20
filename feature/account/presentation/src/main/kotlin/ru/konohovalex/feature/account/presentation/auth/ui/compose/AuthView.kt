package ru.konohovalex.feature.account.presentation.auth.ui.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.konohovalex.core.design.model.Theme
import ru.konohovalex.core.ui.compose.OutlinedThemedTextField
import ru.konohovalex.core.ui.compose.ThemedButton
import ru.konohovalex.core.ui.model.ButtonData
import ru.konohovalex.core.ui.model.ImageWrapper
import ru.konohovalex.core.ui.model.TextWrapper
import ru.konohovalex.core.utils.model.Validator
import ru.konohovalex.feature.account.presentation.R
import ru.konohovalex.feature.account.presentation.auth.model.CredentialsUiModel
import ru.konohovalex.feature.account.presentation.auth.model.PasswordUiModel
import ru.konohovalex.feature.account.presentation.auth.model.UserNameUiModel

private data class CredentialsValidationResult(
    val isUserNameCorrect: Boolean,
    val isPasswordCorrect: Boolean,
) {
    fun isValid() = isUserNameCorrect && isPasswordCorrect
}

@Composable
internal fun AuthView(
    credentialsUiModel: CredentialsUiModel,
    userNameValidator: Validator<UserNameUiModel, TextWrapper?>,
    passwordValidator: Validator<PasswordUiModel, TextWrapper?>,
    onSignUpButtonClick: (CredentialsUiModel) -> Unit,
    onSignInButtonClick: (CredentialsUiModel) -> Unit,
    onDeclineAuthorizationButtonClick: () -> Unit,
) {
    Column {
        val credentialsState = rememberSaveable {
            mutableStateOf(credentialsUiModel)
        }

        val userNameErrorTextWrapper = userNameValidator.validate(credentialsState.value.userNameUiModel)
        val userNameErrorTextWrapperState = remember {
            mutableStateOf(userNameErrorTextWrapper)
        }
        userNameErrorTextWrapperState.value = userNameErrorTextWrapper

        val passwordErrorTextWrapper = passwordValidator.validate(credentialsState.value.passwordUiModel)
        val passwordErrorTextWrapperState = remember {
            mutableStateOf(passwordErrorTextWrapper)
        }
        passwordErrorTextWrapperState.value = passwordErrorTextWrapper

        val credentialsValidationResultState = remember {
            derivedStateOf {
                CredentialsValidationResult(
                    isUserNameCorrect = userNameErrorTextWrapperState.value == null,
                    isPasswordCorrect = passwordErrorTextWrapperState.value == null,
                )
            }
        }

        InputTextFieldsColumn(
            credentialsUiModel = credentialsState.value,
            userNameErrorTextWrapper = userNameErrorTextWrapperState.value,
            onUserNameChanged = getOnUserNameValueChangedCallback(
                userNameValidator = userNameValidator,
                credentialsState = credentialsState,
                userNameErrorState = userNameErrorTextWrapperState,
            ),
            passwordErrorTextWrapper = passwordErrorTextWrapperState.value,
            onPasswordChanged = getOnPasswordValueChangedCallback(
                passwordValidator = passwordValidator,
                credentialsState = credentialsState,
                passwordErrorState = passwordErrorTextWrapperState,
            ),
            isPasswordVisible = credentialsState.value.isPasswordVisible,
            onPasswordVisibilityStateChanged = {
                credentialsState.value = credentialsState.value.copy(isPasswordVisible = it)
            },
        )

        ButtonsColumn(
            credentialsUiModel = credentialsState.value,
            credentialsValidationResult = credentialsValidationResultState.value,
            onSignUpButtonClick = onSignUpButtonClick,
            onSignInButtonClick = onSignInButtonClick,
            declineAuthorizationButtonTextResId = credentialsState.value.declineAuthorizationButtonTextResId,
            onDeclineAuthorizationButtonClick = onDeclineAuthorizationButtonClick,
        )
    }
}

private fun getOnUserNameValueChangedCallback(
    userNameValidator: Validator<UserNameUiModel, TextWrapper?>,
    credentialsState: MutableState<CredentialsUiModel>,
    userNameErrorState: MutableState<TextWrapper?>,
) = { userNameInput: String ->
    UserNameUiModel(userNameInput).let {
        credentialsState.value = credentialsState.value.copy(userNameUiModel = it)
        userNameErrorState.value = userNameValidator.validate(it)
    }
}

private fun getOnPasswordValueChangedCallback(
    passwordValidator: Validator<PasswordUiModel, TextWrapper?>,
    credentialsState: MutableState<CredentialsUiModel>,
    passwordErrorState: MutableState<TextWrapper?>,
) = { passwordInput: String ->
    PasswordUiModel(passwordInput).let {
        credentialsState.value = credentialsState.value.copy(passwordUiModel = it)
        passwordErrorState.value = passwordValidator.validate(it)
    }
}

@Composable
private fun InputTextFieldsColumn(
    credentialsUiModel: CredentialsUiModel,
    userNameErrorTextWrapper: TextWrapper?,
    onUserNameChanged: (userName: String) -> Unit,
    passwordErrorTextWrapper: TextWrapper?,
    onPasswordChanged: (password: String) -> Unit,
    isPasswordVisible: Boolean,
    onPasswordVisibilityStateChanged: (isPasswordVisible: Boolean) -> Unit,
) {
    Column(
        modifier = Modifier
            .height(184.dp)
            .padding(
                start = Theme.paddings.contentMax,
                top = Theme.paddings.contentMedium,
                end = Theme.paddings.contentMax,
            ),
        verticalArrangement = Arrangement.spacedBy(Theme.paddings.contentSmall),
    ) {
        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.TopCenter,
        ) {
            UserNameInputText(
                userNameUiModel = credentialsUiModel.userNameUiModel,
                errorTextWrapper = userNameErrorTextWrapper,
                onUserNameChanged = onUserNameChanged,
            )
        }

        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.TopCenter,
        ) {
            PasswordInputText(
                passwordUiModel = credentialsUiModel.passwordUiModel,
                errorTextWrapper = passwordErrorTextWrapper,
                onPasswordChanged = onPasswordChanged,
                isPasswordVisible = isPasswordVisible,
                onPasswordVisibilityStateChanged = onPasswordVisibilityStateChanged,
            )
        }
    }
}

@Composable
private fun UserNameInputText(
    userNameUiModel: UserNameUiModel,
    errorTextWrapper: TextWrapper?,
    onUserNameChanged: (userName: String) -> Unit,
) {
    val textState = rememberSaveable {
        mutableStateOf(userNameUiModel.value)
    }

    OutlinedThemedTextField(
        textState = textState,
        onValueChanged = onUserNameChanged,
        modifier = Modifier
            .fillMaxWidth(),
        labelTextWrapper = TextWrapper.StringResource(resourceId = R.string.user_name),
        errorTextWrapper = errorTextWrapper,
    )
}

@Composable
private fun PasswordInputText(
    passwordUiModel: PasswordUiModel,
    errorTextWrapper: TextWrapper?,
    onPasswordChanged: (password: String) -> Unit,
    isPasswordVisible: Boolean,
    onPasswordVisibilityStateChanged: (isPasswordVisible: Boolean) -> Unit,
) {
    val textState = rememberSaveable {
        mutableStateOf(passwordUiModel.value)
    }

    OutlinedThemedTextField(
        textState = textState,
        onValueChanged = onPasswordChanged,
        modifier = Modifier
            .fillMaxWidth(),
        labelTextWrapper = TextWrapper.StringResource(resourceId = R.string.password),
        trailingIconImageWrapper = ImageWrapper.ImageResource(
            resourceId = if (isPasswordVisible) R.drawable.ic_visible
            else R.drawable.ic_invisible
        ),
        onTrailingIconClick = {
            // TODO("show or hide real password symbols")
            onPasswordVisibilityStateChanged.invoke(!isPasswordVisible)
        },
        errorTextWrapper = errorTextWrapper,
    )
}

@Composable
private fun ButtonsColumn(
    credentialsUiModel: CredentialsUiModel,
    credentialsValidationResult: CredentialsValidationResult,
    onSignUpButtonClick: (CredentialsUiModel) -> Unit,
    onSignInButtonClick: (CredentialsUiModel) -> Unit,
    @StringRes
    declineAuthorizationButtonTextResId: Int,
    onDeclineAuthorizationButtonClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = Theme.paddings.contentLarge,
                bottom = Theme.paddings.contentLarge,
            ),
        verticalArrangement = Arrangement.spacedBy(Theme.paddings.contentLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            SignUpButton(
                credentialsUiModel = credentialsUiModel,
                credentialsValidationResult = credentialsValidationResult,
                onClick = onSignUpButtonClick
            )

            SignInButton(
                credentialsUiModel = credentialsUiModel,
                credentialsValidationResult = credentialsValidationResult,
                onClick = onSignInButtonClick
            )
        }

        DeclineAuthorizationButton(
            declineAuthorizationButtonTextResId = declineAuthorizationButtonTextResId,
            onClick = onDeclineAuthorizationButtonClick,
        )
    }
}

@Composable
private fun SignInButton(
    credentialsUiModel: CredentialsUiModel,
    credentialsValidationResult: CredentialsValidationResult,
    onClick: (CredentialsUiModel) -> Unit,
) {
    ThemedButton(
        buttonData = ButtonData.Outlined(
            enabled = credentialsValidationResult.isValid(),
            onClick = {
                onClick.invoke(credentialsUiModel)
            },
            content = listOf(
                ButtonData.Content.Text(
                    textWrapper = TextWrapper.StringResource(resourceId = R.string.sign_in),
                ),
            ),
        )
    )
}

@Composable
private fun SignUpButton(
    credentialsUiModel: CredentialsUiModel,
    credentialsValidationResult: CredentialsValidationResult,
    onClick: (CredentialsUiModel) -> Unit,
) {
    ThemedButton(
        buttonData = ButtonData.Outlined(
            enabled = credentialsValidationResult.isValid(),
            onClick = {
                onClick.invoke(credentialsUiModel)
            },
            fillEnabledColor = Theme.palette.backgroundColor,
            content = listOf(
                ButtonData.Content.Text(
                    textWrapper = TextWrapper.StringResource(resourceId = R.string.sign_up),
                ),
            ),
        )
    )
}

@Composable
private fun DeclineAuthorizationButton(
    @StringRes
    declineAuthorizationButtonTextResId: Int,
    onClick: () -> Unit,
) {
    ThemedButton(
        buttonData = ButtonData.Text(
            onClick = onClick,
            content = ButtonData.Content.Text(
                textWrapper = TextWrapper.StringResource(resourceId = declineAuthorizationButtonTextResId),
            ),
        )
    )
}
