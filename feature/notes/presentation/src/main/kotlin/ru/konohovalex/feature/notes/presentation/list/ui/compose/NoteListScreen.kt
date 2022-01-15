package ru.konohovalex.feature.notes.presentation.list.ui.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.konohovalex.core.design.Theme
import ru.konohovalex.core.ui.compose.ThemedCircularProgressBar
import ru.konohovalex.feature.notes.presentation.list.NoteListViewModel
import ru.konohovalex.feature.notes.presentation.list.model.NoteListState

@Composable
internal fun NoteListScreen(
    viewModel: NoteListViewModel = hiltViewModel(),
    onNoteClick: (noteId: String?) -> Unit,
) {
    val viewState = viewModel.state.observeAsState()

    val searchBarTextState = remember {
        mutableStateOf(TextFieldValue(""))
    }
    val onSearchBarTextChanged = { textFieldValue: TextFieldValue ->
        searchBarTextState.value = textFieldValue
        viewModel.filterNotes(searchBarTextState.value.text)
    }

    Scaffold(
        topBar = {
            NoteListTopAppBar(
                searchBarTextState = searchBarTextState,
                onSearchBarTextChanged = onSearchBarTextChanged,
            )
        },
        floatingActionButton = {
            NoteListFloatingActionButton(
                onClick = {
                    onNoteClick.invoke(null)
                },
                modifier = Modifier
                    .padding(PaddingValues(2.dp))
            )
        },
        backgroundColor = Theme.palette.backgroundColor,
    ) {
        // tbd too much recompositions called as PaddingValues changing frequently
        when (val viewStateValue = viewState.value) {
            is NoteListState.Data -> {
                // tbd implement no items state
                NoteList(
                    items = viewStateValue.notes,
                    onNoteClick = onNoteClick,
                )
            }
            // tbd implement error state (for both first and further pages)
            is NoteListState.Error -> {
            }
            is NoteListState.Loading -> {
                ThemedCircularProgressBar()
            }
        }
    }
}
