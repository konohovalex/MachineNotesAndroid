package ru.konohovalex.machinenotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.konohovalex.core.design.Theme
import ru.konohovalex.machinenotes.navigation.MainRoute
import ru.konohovalex.machinenotes.navigation.mainGraph

@AndroidEntryPoint
internal class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            // tbd fix white screen after SplashScreen
        }

        setContent {
            Theme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = MainRoute.Home.buildNavigationRoute(),
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    mainGraph(navController)
                }
            }
        }
    }
}
