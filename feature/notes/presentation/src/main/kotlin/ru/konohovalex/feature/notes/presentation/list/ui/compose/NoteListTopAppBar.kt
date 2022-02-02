package ru.konohovalex.feature.notes.presentation.list.ui.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.konohovalex.core.design.model.Theme
import ru.konohovalex.core.ui.R
import ru.konohovalex.core.ui.compose.OutlinedThemedTextField
import ru.konohovalex.core.ui.model.ImageWrapper
import ru.konohovalex.core.ui.model.TextWrapper

@Composable
internal fun NoteListTopAppBar(onValueChanged: (String) -> Unit) {
    val textState = rememberSaveable {
        mutableStateOf("")
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
            leadingIconImageWrapper = ImageWrapper.ImageResource(resourceId = R.drawable.ic_search),
            trailingIconImageWrapper = ImageWrapper.ImageResource(resourceId = R.drawable.ic_clear),
            onTrailingIconClick = {
                textState.value = ""
                onValueChanged.invoke("")
            },
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
