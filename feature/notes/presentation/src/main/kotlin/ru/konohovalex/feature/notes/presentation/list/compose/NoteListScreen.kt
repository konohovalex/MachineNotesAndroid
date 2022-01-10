package ru.konohovalex.feature.notes.presentation.list.compose

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import ru.konohovalex.core.ui.compose.ThemedCircularProgressBar
import ru.konohovalex.feature.notes.presentation.list.NoteListViewModel
import ru.konohovalex.feature.notes.presentation.list.model.NoteListState

@Composable
internal fun NoteListScreen(
    viewModel: NoteListViewModel = hiltViewModel(),
) {
    val viewState = viewModel.state.observeAsState()

    // tbd implement theme darkening and lightening by user's desire
    when (val viewStateValue = viewState.value) {
        is NoteListState.Data -> {
            // tbd implement no items state
            val context = LocalContext.current

            NoteList(viewStateValue.notes) { noteId ->
                Toast.makeText(
                    context,
                    noteId,
                    Toast.LENGTH_LONG,
                ).show()
            }
        }
        // tbd implement error state (for both first and further pages)
        is NoteListState.Error -> {
        }
        is NoteListState.Loading -> {
            ThemedCircularProgressBar()
        }
    }
}
