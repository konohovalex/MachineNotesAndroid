package ru.konohovalex.feature.account.presentation.auth.ui.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

private data class AuthDataValidationState(
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
    throwable: Throwable?,
) {
    Column {
        val authDataState = remember {
            mutableStateOf(authDataUiModel)
        }

        val authDataValidationState = remember {
            mutableStateOf(
                AuthDataValidationState(
                    isUserNameCorrect = false,
                    isPasswordCorrect = false,
                )
            )
        }

        val userNameErrorState = remember {
            mutableStateOf(userNameValidator.validate(authDataUiModel.userNameUiModel))
        }

        val onUserNameValueChanged = remember {
            getOnUserNameValueChangedCallback(
                userNameValidator = userNameValidator,
                logInButtonEnabledState = authDataValidationState,
                authDataState = authDataState,
                userNameErrorState = userNameErrorState,
            )
        }

        val passwordErrorState = remember {
            mutableStateOf(passwordValidator.validate(authDataUiModel.passwordUiModel))
        }

        val onPasswordValueChanged = remember {
            getOnPasswordValueChangedCallback(
                passwordValidator = passwordValidator,
                logInButtonEnabledState = authDataValidationState,
                authDataState = authDataState,
                passwordErrorState = passwordErrorState,
            )
        }

        InputTextFieldsColumn(
            authDataUiModel = authDataUiModel,
            userNameErrorState = userNameErrorState,
            onUserNameValueChanged = onUserNameValueChanged,
            passwordErrorState = passwordErrorState,
            onPasswordValueChanged = onPasswordValueChanged,
        )

        ButtonsColumn(
            authDataState = authDataState,
            authDataValidationState = authDataValidationState,
            onLogInButtonClick = onLogInButtonClick,
            declineAuthorizationButtonTextResId = authDataUiModel.declineAuthorizationButtonTextResId,
            onDeclineAuthorizationButtonClick = onDeclineAuthorizationButtonClick,
        )

        // tbd if throwable is not null, show error
    }
}

private fun getOnUserNameValueChangedCallback(
    userNameValidator: Validator<UserNameUiModel, TextWrapper?>,
    logInButtonEnabledState: MutableState<AuthDataValidationState>,
    authDataState: MutableState<AuthDataUiModel>,
    userNameErrorState: MutableState<TextWrapper?>,
) = { userNameInput: String ->
    UserNameUiModel(userNameInput).let {
        val validationResult = userNameValidator.validate(it)
        logInButtonEnabledState.value =
            logInButtonEnabledState.value.copy(
                isUserNameCorrect = validationResult == null,
            )
        authDataState.value = authDataState.value.copy(userNameUiModel = it)
        userNameErrorState.value = validationResult
    }
}

private fun getOnPasswordValueChangedCallback(
    passwordValidator: Validator<PasswordUiModel, TextWrapper?>,
    logInButtonEnabledState: MutableState<AuthDataValidationState>,
    authDataState: MutableState<AuthDataUiModel>,
    passwordErrorState: MutableState<TextWrapper?>,
) = { passwordInput: String ->
    PasswordUiModel(passwordInput).let {
        val validationResult = passwordValidator.validate(it)
        logInButtonEnabledState.value =
            logInButtonEnabledState.value.copy(
                isPasswordCorrect = validationResult == null,
            )
        authDataState.value = authDataState.value.copy(passwordUiModel = it)
        passwordErrorState.value = validationResult
    }
}

@Composable
private fun InputTextFieldsColumn(
    authDataUiModel: AuthDataUiModel,
    userNameErrorState: State<TextWrapper?>,
    onUserNameValueChanged: (String) -> Unit,
    passwordErrorState: State<TextWrapper?>,
    onPasswordValueChanged: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(
                start = Theme.paddings.contentMax,
                top = Theme.paddings.contentMedium,
                end = Theme.paddings.contentMax,
            ),
    ) {
        UserNameInputText(
            userNameUiModel = authDataUiModel.userNameUiModel,
            errorState = userNameErrorState,
            onValueChanged = onUserNameValueChanged,
        )

        PasswordInputText(
            passwordUiModel = authDataUiModel.passwordUiModel,
            errorState = passwordErrorState,
            onValueChanged = onPasswordValueChanged,
        )
    }
}

@Composable
private fun UserNameInputText(
    userNameUiModel: UserNameUiModel,
    errorState: State<TextWrapper?>,
    onValueChanged: (String) -> Unit,
) {
    val textState = remember {
        mutableStateOf(userNameUiModel.value)
    }

    val labelTextWrapper = remember {
        TextWrapper.StringResource(resourceId = R.string.user_name)
    }

    OutlinedThemedTextField(
        textState = textState,
        onValueChanged = onValueChanged,
        modifier = Modifier
            .fillMaxWidth(),
        labelTextWrapper = labelTextWrapper,
        errorState = errorState,
    )
}

@Composable
private fun PasswordInputText(
    passwordUiModel: PasswordUiModel,
    errorState: State<TextWrapper?>,
    onValueChanged: (String) -> Unit,
) {
    val textState = remember {
        mutableStateOf(passwordUiModel.value)
    }

    val visibleImageWrapper = remember {
        ImageWrapper.ImageResource(resourceId = R.drawable.ic_visible)
    }
    val invisibleImageWrapper = remember {
        ImageWrapper.ImageResource(resourceId = R.drawable.ic_invisible)
    }
    val isPasswordVisibleState = remember {
        mutableStateOf(false)
    }
    val trailingIconImageWrapperState = remember {
        mutableStateOf(invisibleImageWrapper)
    }
    val onTrailingIconClick = remember {
        {
            // tbd show or hide real password symbols
            isPasswordVisibleState.value = !isPasswordVisibleState.value
            trailingIconImageWrapperState.value =
                if (isPasswordVisibleState.value) visibleImageWrapper
                else invisibleImageWrapper
        }
    }

    val labelTextWrapper = remember {
        TextWrapper.StringResource(resourceId = R.string.password)
    }

    OutlinedThemedTextField(
        textState = textState,
        onValueChanged = onValueChanged,
        modifier = Modifier
            .fillMaxWidth(),
        labelTextWrapper = labelTextWrapper,
        trailingIconImageWrapperState = trailingIconImageWrapperState,
        onTrailingIconClick = onTrailingIconClick,
        errorState = errorState,
    )
}

@Composable
private fun ButtonsColumn(
    authDataState: State<AuthDataUiModel>,
    authDataValidationState: State<AuthDataValidationState>,
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
            authDataState = authDataState,
            authDataValidationState = authDataValidationState,
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
    authDataState: State<AuthDataUiModel>,
    authDataValidationState: State<AuthDataValidationState>,
    onClick: (authDataUiModel: AuthDataUiModel) -> Unit,
) {
    val rememberedOnClick = remember {
        {
            onClick.invoke(authDataState.value)
        }
    }

    val content = remember {
        listOf(
            ButtonData.Content.Text(
                textWrapper = TextWrapper.StringResource(resourceId = R.string.log_in),
            ),
        )
    }

    val authDataValidationStateValue by authDataValidationState
    ThemedButton(
        buttonData = ButtonData.Outlined(
            enabled = authDataValidationStateValue.isValid(),
            onClick = rememberedOnClick,
            content = content,
        )
    )
}

@Composable
private fun DeclineAuthorizationButton(
    @StringRes
    declineAuthorizationButtonTextResId: Int,
    onClick: () -> Unit,
) {
    val content = remember {
        ButtonData.Content.Text(
            textWrapper = TextWrapper.StringResource(resourceId = declineAuthorizationButtonTextResId),
        )
    }

    ThemedButton(
        buttonData = ButtonData.Text(
            onClick = onClick,
            content = content,
        )
    )
}
