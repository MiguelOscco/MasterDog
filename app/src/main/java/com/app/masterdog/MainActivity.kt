package com.app.masterdog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.app.masterdog.ui.screens.ActualizarScreen
import com.app.masterdog.ui.theme.MasterDogTheme


sealed class Screen{

    object Inicio : Screen()
    object Registrar: Screen()
    object Buscar: Screen()


}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme{
                MasterDog()
            }
        }
    }
}

@Composable
fun MasterDog(){

    var currentScreen by remember { mutableStateOf<Screen>(Screen.Registrar) }



    when(currentScreen){


        is Screen.Registrar -> ActualizarScreen (
            onNavigateToInicio = {currentScreen= Screen.Inicio},
            onNavigateToBuscar = {currentScreen= Screen.Buscar},
            onNavigateToRegistrar = {currentScreen= Screen.Registrar}



        )







        else -> {}
    }



}
