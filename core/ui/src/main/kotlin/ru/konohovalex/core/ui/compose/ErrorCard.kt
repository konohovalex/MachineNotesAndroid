package ru.konohovalex.core.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import ru.konohovalex.core.design.model.Theme
import ru.konohovalex.core.ui.R
import ru.konohovalex.core.ui.model.ButtonData
import ru.konohovalex.core.ui.model.ImageWrapper
import ru.konohovalex.core.ui.model.TextWrapper

@Composable
fun ErrorCard(
    modifier: Modifier = Modifier,
    titleTextWrapper: TextWrapper = TextWrapper.StringResource(resourceId = R.string.error_title),
    descriptionTextWrapper: TextWrapper? = null,
    actionButtonTextWrapper: TextWrapper = TextWrapper.StringResource(resourceId = R.string.retry),
    onActionButtonClick: () -> Unit,
) {
    ThemedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Theme.paddings.contentMax),
        borderColor = Theme.palette.errorColor,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Theme.paddings.contentSmall),
            verticalArrangement = Arrangement.spacedBy(Theme.paddings.contentMedium),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ThemedImage(
                imageWrapper = ImageWrapper.ImageResource(resourceId = R.drawable.ic_error),
                iconTintColor = Theme.palette.errorColor,
            )

            ThemedText(
                themedTextType = ThemedTextType.TITLE,
                textWrapper = titleTextWrapper,
                textAlign = TextAlign.Center,
            )

            descriptionTextWrapper?.let {
                ThemedText(
                    themedTextType = ThemedTextType.BODY,
                    textWrapper = it,
                    textAlign = TextAlign.Center,
                )
            }

            ThemedButton(
                buttonData = ButtonData.Outlined(
                    onClick = onActionButtonClick,
                    content = listOf(ButtonData.Content.Text(actionButtonTextWrapper)),
                ),
            )
        }
    }
}
