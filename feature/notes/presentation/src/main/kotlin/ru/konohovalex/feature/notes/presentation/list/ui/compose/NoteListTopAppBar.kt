package ru.konohovalex.feature.notes.presentation.list.ui.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import ru.konohovalex.core.design.Theme
import ru.konohovalex.core.ui.R
import ru.konohovalex.core.ui.compose.ThemedTextField
import ru.konohovalex.core.ui.compose.model.ImageWrapper
import ru.konohovalex.core.ui.compose.model.TextWrapper

@Composable
internal fun NoteListTopAppBar(
    searchBarTextState: State<TextFieldValue>,
    onSearchBarTextChanged: (TextFieldValue) -> Unit,
) {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth(),
        backgroundColor = Theme.palette.backgroundColor,
        contentPadding = PaddingValues(Theme.paddings.contentDefault),
    ) {
        ThemedTextField(
            textState = searchBarTextState,
            onValueChanged = onSearchBarTextChanged,
            modifier = Modifier
                .fillMaxWidth(),
            placeholderTextWrapper = TextWrapper.StringResource(resourceId = R.string.search),
            leadingIconImageWrapper = ImageWrapper.ImageResource(resourceId = R.drawable.ic_search),
        )
    }
}

@Preview
@Composable
private fun NoteListTopAppBarPreview() {
    val searchBarTextState = derivedStateOf { TextFieldValue("") }

    Theme {
        NoteListTopAppBar(
            searchBarTextState = searchBarTextState,
            onSearchBarTextChanged = {},
        )
    }
}
