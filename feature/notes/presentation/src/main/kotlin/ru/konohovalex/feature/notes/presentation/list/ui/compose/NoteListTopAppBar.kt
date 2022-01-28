package ru.konohovalex.feature.notes.presentation.list.ui.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.konohovalex.core.design.model.Theme
import ru.konohovalex.core.ui.R
import ru.konohovalex.core.ui.compose.OutlinedThemedTextField
import ru.konohovalex.core.ui.model.ImageWrapper
import ru.konohovalex.core.ui.model.TextWrapper

@Composable
internal fun NoteListTopAppBar(onValueChanged: (String) -> Unit) {
    val leadingIconImageWrapperState = derivedStateOf {
        ImageWrapper.ImageResource(resourceId = R.drawable.ic_search)
    }

    val trailingIconImageWrapperState = derivedStateOf {
        ImageWrapper.ImageResource(resourceId = R.drawable.ic_clear)
    }
    val textState = remember {
        mutableStateOf("")
    }
    val onTrailingIconClick = remember {
        {
            textState.value = ""
            onValueChanged.invoke("")
        }
    }

    TopAppBar(
        modifier = Modifier
            .fillMaxWidth(),
        backgroundColor = Theme.palette.backgroundColor,
        contentPadding = PaddingValues(Theme.paddings.contentSmall),
    ) {
        OutlinedThemedTextField(
            textState = textState,
            onValueChanged = onValueChanged,
            modifier = Modifier
                .fillMaxWidth(),
            placeholderTextWrapper = TextWrapper.StringResource(resourceId = R.string.search),
            leadingIconImageWrapperState = leadingIconImageWrapperState,
            trailingIconImageWrapperState = trailingIconImageWrapperState,
            onTrailingIconClick = onTrailingIconClick,
        )
    }
}

@Preview
@Composable
private fun NoteListTopAppBarPreview() {
    Theme {
        NoteListTopAppBar {}
    }
}
