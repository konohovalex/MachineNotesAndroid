package ru.konohovalex.feature.notes.presentation.list.ui.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.konohovalex.core.design.Theme
import ru.konohovalex.core.presentation.arch.viewevent.ViewEventHandler
import ru.konohovalex.core.ui.R
import ru.konohovalex.core.ui.compose.OutlinedThemedTextField
import ru.konohovalex.core.ui.compose.model.ImageWrapper
import ru.konohovalex.core.ui.compose.model.TextWrapper
import ru.konohovalex.feature.notes.presentation.list.model.NoteListScreenViewEvent

@Composable
internal fun NoteListTopAppBar(viewEventHandler: ViewEventHandler<NoteListScreenViewEvent>) {
    val onSearchBarTextChanged = { text: String ->
        viewEventHandler.handle(NoteListScreenViewEvent.GetNotes(filter = text))
    }

    TopAppBar(
        modifier = Modifier
            .fillMaxWidth(),
        backgroundColor = Theme.palette.backgroundColor,
        contentPadding = PaddingValues(Theme.paddings.contentDefault),
    ) {
        OutlinedThemedTextField(
            text = "",
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
    val eventHandler = ViewEventHandler<NoteListScreenViewEvent> { }

    Theme {
        NoteListTopAppBar(eventHandler)
    }
}
