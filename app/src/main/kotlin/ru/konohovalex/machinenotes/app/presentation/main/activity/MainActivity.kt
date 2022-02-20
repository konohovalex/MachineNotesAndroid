package ru.konohovalex.machinenotes.app.presentation.main.activity

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import ru.konohovalex.core.presentation.extension.changeLocale
import ru.konohovalex.machinenotes.R
import ru.konohovalex.machinenotes.app.presentation.main.ui.compose.MainScreen
import ru.konohovalex.machinenotes.app.presentation.main.viewmodel.MainViewModel
import java.util.Locale

@AndroidEntryPoint
internal class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setContent {
            // TODO("implement dependency injection?")
            val viewModel = hiltViewModel<MainViewModel>()

            val localeState = rememberSaveable {
                mutableStateOf<Locale?>(null)
            }
            val isDarkThemeState = rememberSaveable {
                mutableStateOf(isSystemInDarkTheme())
            }

            MainScreen(
                viewStateProvider = viewModel,
                viewEventHandler = viewModel,
                onThemeModeChanged = {
                    if (it != isDarkThemeState.value) {
                        isDarkThemeState.value = it
                        window.apply {
                            val backgroundColor = ContextCompat.getColor(
                                applicationContext,
                                if (isDarkThemeState.value) R.color.gray_800 else R.color.gray_200,
                            )
                            statusBarColor = backgroundColor
                            navigationBarColor = backgroundColor
                        }
                    }
                },
                onLocaleChanged = {
                    if (it != localeState.value) {
                        localeState.value = it
                        localeState.value?.let(::changeLocale)
                    }
                },
                onBackPressed = {
                    finish()
                },
            )
        }
    }

    private fun isSystemInDarkTheme() =
        resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
}
