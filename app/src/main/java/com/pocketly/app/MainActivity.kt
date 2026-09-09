package com.pocketly.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.pocketly.app.data.UserPreferences
import com.pocketly.app.ui.screens.HomeScreen
import com.pocketly.app.ui.screens.LoginScreen
import com.pocketly.app.ui.screens.SplashScreen
import com.pocketly.app.ui.theme.PocketlyTheme

enum class AppDestination {
    SPLASH,
    LOGIN,
    HOME
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            )
        )
        setContent {
            PocketlyTheme {
                val context = LocalContext.current
                val userPreferences = remember { UserPreferences(context) }

                var currentDestination by remember {
                    mutableStateOf(AppDestination.SPLASH)
                }

                Crossfade(
                    targetState = currentDestination,
                    animationSpec = tween(durationMillis = 400),
                    label = "AppScreenTransition"
                ) { destination ->
                    when (destination) {
                        AppDestination.SPLASH -> {
                            SplashScreen(
                                onSplashFinished = {
                                    currentDestination = if (userPreferences.isLoggedIn && userPreferences.rememberMe) {
                                        AppDestination.HOME
                                    } else {
                                        AppDestination.LOGIN
                                    }
                                }
                            )
                        }
                        AppDestination.LOGIN -> {
                            LoginScreen(
                                onLoginSuccess = {
                                    currentDestination = AppDestination.HOME
                                }
                            )
                        }
                        AppDestination.HOME -> {
                            HomeScreen(
                                onSignOut = {
                                    userPreferences.logout()
                                    currentDestination = AppDestination.LOGIN
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
