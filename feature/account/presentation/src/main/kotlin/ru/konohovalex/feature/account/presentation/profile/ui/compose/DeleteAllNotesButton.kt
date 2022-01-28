package ru.konohovalex.feature.account.presentation.profile.ui.compose

import androidx.compose.runtime.Composable
import ru.konohovalex.core.design.model.Theme
import ru.konohovalex.core.ui.compose.ThemedButton
import ru.konohovalex.core.ui.model.ButtonData
import ru.konohovalex.core.ui.model.ImageWrapper
import ru.konohovalex.core.ui.model.TextWrapper
import ru.konohovalex.feature.account.presentation.R

@Composable
internal fun DeleteAllNotesButton(onClick: () -> Unit) {
    ThemedButton(
        buttonData = ButtonData.Regular(
            onClick = onClick,
            fillEnabledColor = Theme.palette.errorColor,
            content = listOf(
                ButtonData.Content.Text(
                    textWrapper = TextWrapper.StringResource(resourceId = R.string.delete_all_notes)
                ),
                ButtonData.Content.Image(
                    imageWrapper = ImageWrapper.ImageResource(resourceId = R.drawable.ic_delete)
                ),
            ),
            contentSpacing = Theme.paddings.contentSmall,
        )
    )
}
