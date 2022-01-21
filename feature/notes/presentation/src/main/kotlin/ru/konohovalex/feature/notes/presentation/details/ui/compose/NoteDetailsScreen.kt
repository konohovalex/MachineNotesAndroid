package ru.konohovalex.feature.notes.presentation.details.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.konohovalex.core.design.Theme
import ru.konohovalex.core.ui.compose.ThemedCard
import ru.konohovalex.core.ui.compose.ThemedText
import ru.konohovalex.core.ui.compose.ThemedTextType
import ru.konohovalex.core.ui.compose.model.TextWrapper

@Composable
internal fun NoteDetailsScreen(
    noteId: String,
) {
    Scaffold(
        topBar = {
            NoteDetailsTopAppBar()
        },
        floatingActionButton = {
            NoteDetailsFloatingActionButton(
                onClick = {
                    // tbd show popping-up fabs for image/audio addition
                },
                modifier = Modifier
                    .padding(PaddingValues(Theme.paddings.floatingActionButtonCompensation)),
            )
        },
        backgroundColor = Theme.palette.backgroundColor,
    ) {
        ThemedCard(
            modifier = Modifier
                .fillMaxSize()
                .padding(Theme.paddings.contentDefault),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                ThemedText(
                    themedTextType = ThemedTextType.HEADLINE,
                    textWrapper = TextWrapper.PlainText(value = noteId),
                )
            }
        }
    }
}
