package ru.konohovalex.machinenotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.konohovalex.core.design.Theme
import ru.konohovalex.feature.notes.presentation.list.navigation.NotesRoute
import ru.konohovalex.feature.notes.presentation.list.navigation.noteListFlow

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Theme {
                Surface(
                    modifier = Modifier
                        .background(
                            color = Theme.palette.backgroundColor,
                        ),
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = NotesRoute.NoteList.route,
                    ) {
                        noteListFlow(navController)
                    }
                }
            }
        }
    }
}
