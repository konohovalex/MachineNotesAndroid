package ru.konohovalex.feature.account.presentation.profile.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import ru.konohovalex.core.design.model.Theme
import ru.konohovalex.core.ui.compose.SizedSpacer
import ru.konohovalex.core.ui.compose.ThemedButton
import ru.konohovalex.core.ui.compose.ThemedCard
import ru.konohovalex.core.ui.compose.ThemedText
import ru.konohovalex.core.ui.compose.ThemedTextType
import ru.konohovalex.core.ui.model.ButtonData
import ru.konohovalex.core.ui.model.TextWrapper
import ru.konohovalex.feature.account.presentation.R
import ru.konohovalex.feature.account.presentation.profile.extension.isAuthorized
import ru.konohovalex.feature.account.presentation.profile.model.NotesStatistics
import ru.konohovalex.feature.account.presentation.profile.model.ProfileUiModel

@Composable
internal fun ProfileView(
    profileUiModel: ProfileUiModel,
    modifier: Modifier,
    onLogOutButtonClick: () -> Unit,
    onDeleteAccountButtonClick: () -> Unit,
    onLogInButtonClick: () -> Unit,
) {
    ThemedCard(
        modifier = modifier
            .padding(horizontal = Theme.paddings.contentSmall),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Theme.paddings.contentSmall),
            verticalArrangement = Arrangement.spacedBy(
                space = Theme.paddings.contentMedium,
                alignment = Alignment.CenterVertically,
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Texts(profileUiModel)

            Buttons(
                isAuthorized = profileUiModel.isAuthorized(),
                onLogOutButtonClick = onLogOutButtonClick,
                onDeleteAccountButtonClick = onDeleteAccountButtonClick,
                onLogInButtonClick = onLogInButtonClick,
            )
        }
    }
}

@Composable
private fun Texts(profileUiModel: ProfileUiModel) {
    UserNameText(profileUiModel)

    NotesStatisticsTexts(profileUiModel.notesStatistics)
}

@Composable
private fun UserNameText(profileUiModel: ProfileUiModel) {
    val userNameTextWrapper =
        if (profileUiModel.isAuthorized()) TextWrapper.PlainText(value = profileUiModel.name)
        else TextWrapper.StringResource(resourceId = R.string.guest)

    ThemedText(
        themedTextType = ThemedTextType.HEADLINE,
        textWrapper = userNameTextWrapper,
    )
}

@Composable
private fun NotesStatisticsTexts(notesStatistics: NotesStatistics) {
    NotesAmountText(notesStatistics.notesAmount)

    ImageAndAudioStatistics(notesStatistics)
}

@Composable
private fun NotesAmountText(notesAmount: Int) {
    ThemedText(
        themedTextType = ThemedTextType.TITLE,
        textWrapper = TextWrapper.QuantityStringResource(
            resourceId = R.plurals.notes_statistics,
            quantity = notesAmount,
            formatArgs = arrayOf(notesAmount),
        ),
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun ImageAndAudioStatistics(notesStatistics: NotesStatistics) {
    with(notesStatistics) {
        ThemedText(
            themedTextType = ThemedTextType.LABEL,
            textWrapper = TextWrapper.Composite(
                textWrappers = listOf(
                    TextWrapper.QuantityStringResource(
                        resourceId = R.plurals.audio_files_statistics,
                        quantity = audioFilesAmount,
                        formatArgs = arrayOf(
                            audioFilesAmount,
                            audioFilesWithSpeechRecognitionAmount,
                        ),
                    ),
                    TextWrapper.QuantityStringResource(
                        resourceId = R.plurals.images_statistics,
                        quantity = imagesAmount,
                        formatArgs = arrayOf(
                            imagesAmount,
                            imagesWithObjectRecognitionAmount,
                        ),
                    )
                ),
                divider = "\n",
            ),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun Buttons(
    isAuthorized: Boolean,
    onLogOutButtonClick: () -> Unit,
    onDeleteAccountButtonClick: () -> Unit,
    onLogInButtonClick: () -> Unit,
) {
    if (isAuthorized) {
        LogOutButton(onLogOutButtonClick)

        DeleteAccountButton(onDeleteAccountButtonClick)
    }
    else {
        SizedSpacer(size = Theme.paddings.contentExtraLarge)

        LogInButton(onLogInButtonClick)
    }
}

@Composable
private fun LogOutButton(onClick: () -> Unit) {
    ThemedButton(
        buttonData = ButtonData.Text(
            onClick = onClick,
            content = ButtonData.Content.Text(
                textWrapper = TextWrapper.StringResource(resourceId = R.string.log_out),
            ),
        ),
    )
}

@Composable
private fun DeleteAccountButton(onClick: () -> Unit) {
    ThemedButton(
        buttonData = ButtonData.Text(
            onClick = onClick,
            content = ButtonData.Content.Text(
                textWrapper = TextWrapper.StringResource(resourceId = R.string.delete_account),
                textColor = Theme.palette.errorColor,
            ),
        ),
    )
}

@Composable
private fun LogInButton(onClick: () -> Unit) {
    ThemedButton(
        buttonData = ButtonData.Outlined(
            onClick = onClick,
            content = listOf(
                ButtonData.Content.Text(
                    textWrapper = TextWrapper.StringResource(resourceId = R.string.log_in),
                )
            ),
        ),
    )
}
