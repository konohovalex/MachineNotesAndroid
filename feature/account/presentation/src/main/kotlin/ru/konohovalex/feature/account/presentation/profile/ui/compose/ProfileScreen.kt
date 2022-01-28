package ru.konohovalex.feature.account.presentation.profile.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.konohovalex.core.design.model.Theme
import ru.konohovalex.core.presentation.arch.viewevent.ViewEventHandler
import ru.konohovalex.core.presentation.arch.viewstate.ViewStateProvider
import ru.konohovalex.core.ui.compose.Logo
import ru.konohovalex.core.ui.compose.SizedSpacer
import ru.konohovalex.core.ui.compose.ThemedCircularProgressBar
import ru.konohovalex.feature.account.presentation.profile.model.ProfileUiModel
import ru.konohovalex.feature.account.presentation.profile.model.ProfileViewEvent
import ru.konohovalex.feature.account.presentation.profile.model.ProfileViewState

@Composable
internal fun ProfileScreen(
    viewStateProvider: ViewStateProvider<ProfileViewState>,
    viewEventHandler: ViewEventHandler<ProfileViewEvent>,
    navigateToAuth: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = Theme.paddings.contentExtraLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Logo()

        val viewState = viewStateProvider.viewState.observeAsState()

        val onLogOutButtonClick = remember {
            {
                viewEventHandler.handle(ProfileViewEvent.LogOut)
            }
        }
        val onDeleteAccountButtonClick = remember {
            {
                viewEventHandler.handle(ProfileViewEvent.DeleteAccount)
            }
        }
        val onDeleteAllNotesButtonClick = remember {
            {
                viewEventHandler.handle(ProfileViewEvent.DeleteAllNotes)
            }
        }

        when (val viewStateValue = viewState.value) {
            is ProfileViewState.Idle -> viewEventHandler.handle(ProfileViewEvent.GetProfile)
            is ProfileViewState.Loading -> LoadingState()
            is ProfileViewState.Data -> DataState(
                profileUiModel = viewStateValue.profileUiModel,
                onLogOutButtonClick = onLogOutButtonClick,
                onDeleteAccountButtonClick = onDeleteAccountButtonClick,
                onDeleteAllNotesButtonClick = onDeleteAllNotesButtonClick,
                navigateToAuth = navigateToAuth,
            )
            is ProfileViewState.Error -> ErrorState()
        }
    }
}

@Composable
private fun LoadingState() {
    ThemedCircularProgressBar(
        modifier = Modifier
            .fillMaxSize(),
    )
}

@Composable
private fun ColumnScope.DataState(
    profileUiModel: ProfileUiModel,
    onLogOutButtonClick: () -> Unit,
    onDeleteAccountButtonClick: () -> Unit,
    onDeleteAllNotesButtonClick: () -> Unit,
    navigateToAuth: () -> Unit,
) {
    ProfileView(
        profileUiModel = profileUiModel,
        modifier = Modifier
            .weight(1f),
        onLogOutButtonClick = onLogOutButtonClick,
        onDeleteAccountButtonClick = onDeleteAccountButtonClick,
        onLogInButtonClick = navigateToAuth,
    )

    SizedSpacer(size = Theme.paddings.contentExtraLarge)

    DeleteAllNotesButton(onDeleteAllNotesButtonClick)
}

@Composable
private fun ErrorState() {
    // tbd implement error state
}