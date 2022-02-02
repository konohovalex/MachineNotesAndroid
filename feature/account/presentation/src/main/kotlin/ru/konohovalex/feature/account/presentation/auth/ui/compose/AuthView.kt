package ru.konohovalex.feature.account.presentation.auth.ui.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import ru.konohovalex.feature.account.presentation.auth.model.AuthDataUiModel
import ru.konohovalex.feature.account.presentation.auth.model.PasswordUiModel
import ru.konohovalex.feature.account.presentation.auth.model.UserNameUiModel

private data class AuthDataValidationResult(
    val isUserNameCorrect: Boolean,
    val isPasswordCorrect: Boolean,
) {
    fun isValid() = isUserNameCorrect && isPasswordCorrect
}

@Composable
internal fun AuthView(
    authDataUiModel: AuthDataUiModel,
    userNameValidator: Validator<UserNameUiModel, TextWrapper?>,
    passwordValidator: Validator<PasswordUiModel, TextWrapper?>,
    onLogInButtonClick: (AuthDataUiModel) -> Unit,
    onDeclineAuthorizationButtonClick: () -> Unit,
) {
    Column {
        val authDataState = rememberSaveable {
            mutableStateOf(authDataUiModel)
        }

        val userNameErrorTextWrapper = userNameValidator.validate(authDataState.value.userNameUiModel)
        val userNameErrorTextWrapperState = remember {
            mutableStateOf(userNameErrorTextWrapper)
        }
        userNameErrorTextWrapperState.value = userNameErrorTextWrapper

        val passwordErrorTextWrapper = passwordValidator.validate(authDataState.value.passwordUiModel)
        val passwordErrorTextWrapperState = remember {
            mutableStateOf(passwordErrorTextWrapper)
        }
        passwordErrorTextWrapperState.value = passwordErrorTextWrapper

        val authDataValidationResultState = remember {
            derivedStateOf {
                AuthDataValidationResult(
                    isUserNameCorrect = userNameErrorTextWrapperState.value == null,
                    isPasswordCorrect = passwordErrorTextWrapperState.value == null,
                )
            }
        }

        InputTextFieldsColumn(
            authDataUiModel = authDataState.value,
            userNameErrorTextWrapper = userNameErrorTextWrapperState.value,
            onUserNameChanged = getOnUserNameValueChangedCallback(
                userNameValidator = userNameValidator,
                authDataState = authDataState,
                userNameErrorState = userNameErrorTextWrapperState,
            ),
            passwordErrorTextWrapper = passwordErrorTextWrapperState.value,
            onPasswordChanged = getOnPasswordValueChangedCallback(
                passwordValidator = passwordValidator,
                authDataState = authDataState,
                passwordErrorState = passwordErrorTextWrapperState,
            ),
            isPasswordVisible = authDataState.value.isPasswordVisible,
            onPasswordVisibilityStateChanged = {
                authDataState.value = authDataState.value.copy(isPasswordVisible = it)
            },
        )

        ButtonsColumn(
            authData = authDataState.value,
            authDataValidationResult = authDataValidationResultState.value,
            onLogInButtonClick = onLogInButtonClick,
            declineAuthorizationButtonTextResId = authDataState.value.declineAuthorizationButtonTextResId,
            onDeclineAuthorizationButtonClick = onDeclineAuthorizationButtonClick,
        )
    }
}

private fun getOnUserNameValueChangedCallback(
    userNameValidator: Validator<UserNameUiModel, TextWrapper?>,
    authDataState: MutableState<AuthDataUiModel>,
    userNameErrorState: MutableState<TextWrapper?>,
) = { userNameInput: String ->
    UserNameUiModel(userNameInput).let {
        authDataState.value = authDataState.value.copy(userNameUiModel = it)
        userNameErrorState.value = userNameValidator.validate(it)
    }
}

private fun getOnPasswordValueChangedCallback(
    passwordValidator: Validator<PasswordUiModel, TextWrapper?>,
    authDataState: MutableState<AuthDataUiModel>,
    passwordErrorState: MutableState<TextWrapper?>,
) = { passwordInput: String ->
    PasswordUiModel(passwordInput).let {
        authDataState.value = authDataState.value.copy(passwordUiModel = it)
        passwordErrorState.value = passwordValidator.validate(it)
    }
}

@Composable
private fun InputTextFieldsColumn(
    authDataUiModel: AuthDataUiModel,
    userNameErrorTextWrapper: TextWrapper?,
    onUserNameChanged: (String) -> Unit,
    passwordErrorTextWrapper: TextWrapper?,
    onPasswordChanged: (String) -> Unit,
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
                userNameUiModel = authDataUiModel.userNameUiModel,
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
                passwordUiModel = authDataUiModel.passwordUiModel,
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
    onUserNameChanged: (String) -> Unit,
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
    onPasswordChanged: (String) -> Unit,
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
            // tbd show or hide real password symbols
            onPasswordVisibilityStateChanged.invoke(!isPasswordVisible)
        },
        errorTextWrapper = errorTextWrapper,
    )
}

@Composable
private fun ButtonsColumn(
    authData: AuthDataUiModel,
    authDataValidationResult: AuthDataValidationResult,
    onLogInButtonClick: (authDataUiModel: AuthDataUiModel) -> Unit,
    @StringRes
    declineAuthorizationButtonTextResId: Int,
    onDeclineAuthorizationButtonClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = Theme.paddings.contentLarge * 2,
                bottom = Theme.paddings.contentLarge,
            ),
        verticalArrangement = Arrangement.spacedBy(Theme.paddings.contentLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LogInButton(
            authData = authData,
            authDataValidationResult = authDataValidationResult,
            onClick = onLogInButtonClick
        )

        DeclineAuthorizationButton(
            declineAuthorizationButtonTextResId = declineAuthorizationButtonTextResId,
            onClick = onDeclineAuthorizationButtonClick,
        )
    }
}

@Composable
private fun LogInButton(
    authData: AuthDataUiModel,
    authDataValidationResult: AuthDataValidationResult,
    onClick: (authDataUiModel: AuthDataUiModel) -> Unit,
) {
    ThemedButton(
        buttonData = ButtonData.Outlined(
            enabled = authDataValidationResult.isValid(),
            onClick = {
                onClick.invoke(authData)
            },
            content = listOf(
                ButtonData.Content.Text(
                    textWrapper = TextWrapper.StringResource(resourceId = R.string.log_in),
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
