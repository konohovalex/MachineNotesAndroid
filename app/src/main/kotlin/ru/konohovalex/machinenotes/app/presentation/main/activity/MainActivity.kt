package ru.konohovalex.machinenotes.app.presentation.main.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import ru.konohovalex.machinenotes.app.presentation.main.ui.compose.MainScreen
import ru.konohovalex.machinenotes.app.presentation.main.viewmodel.MainViewModel

@AndroidEntryPoint
internal class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setContent {
            // tbd implement dependency injection
            val viewModel = hiltViewModel<MainViewModel>()

            MainScreen(
                viewStateProvider = viewModel,
                viewEventHandler = viewModel,
            )
        }
    }
}
