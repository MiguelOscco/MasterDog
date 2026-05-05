package com.app.masterdog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.app.masterdog.ui.screens.LoginScreen
import com.app.masterdog.ui.screens.PetsScreen
import com.app.masterdog.ui.theme.MasterDogTheme

sealed class Screen {
    object Inicio : Screen()
    object Mascotas : Screen()
    object Registrar : Screen()
    object Buscar : Screen()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MasterDogTheme(dynamicColor = false) {
                MasterDog()
            }
        }
    }
}

@Composable
fun MasterDog() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Inicio) }

    when (currentScreen) {
        Screen.Inicio -> LoginScreen(
            onLoginSuccess = { currentScreen = Screen.Mascotas },
            onGoogleLogin = { currentScreen = Screen.Mascotas }
        )

        Screen.Mascotas -> PetsScreen(
            onBackToLogin = { currentScreen = Screen.Inicio }
        )

        Screen.Registrar,
        Screen.Buscar -> Unit
    }
}
