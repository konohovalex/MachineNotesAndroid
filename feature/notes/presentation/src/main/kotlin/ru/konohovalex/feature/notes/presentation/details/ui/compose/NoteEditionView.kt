package ru.konohovalex.feature.notes.presentation.details.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import ru.konohovalex.core.design.model.Theme
import ru.konohovalex.core.ui.compose.BasicThemedTextField
import ru.konohovalex.feature.notes.presentation.details.model.NoteContentUiModel
import ru.konohovalex.feature.notes.presentation.details.model.NoteUiModel

@Composable
internal fun NoteEditionView(
    noteUiModel: NoteUiModel,
    onTextChanged: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Theme.paddings.contentSmall),
        verticalArrangement = Arrangement.spacedBy(Theme.paddings.contentExtraSmall)
    ) {
        val textState = rememberSaveable {
            mutableStateOf(
                buildString {
                    noteUiModel.noteContent.forEach {
                        if (it is NoteContentUiModel.Text) append(it.content)
                    }
                }
            )
        }
        BasicThemedTextField(
            textState,
            onValueChanged = onTextChanged,
        )
    }
}
