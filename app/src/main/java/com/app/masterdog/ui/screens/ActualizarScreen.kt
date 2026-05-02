package com.app.masterdog.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.masterdog.ui.theme.MasterError
import com.app.masterdog.ui.theme.MasterPurple
import com.app.masterdog.ui.theme.MasterPurpleDark
import com.app.masterdog.ui.theme.MasterPurpleLight
import com.app.masterdog.ui.theme.MasterSuccess
import com.app.masterdog.ui.theme.MasterTeal
import com.app.masterdog.ui.theme.MasterWhite
import com.app.masterdog.ui.theme.MasterYellow

import kotlin.String


//desde aca va


@Composable
fun FooterDivider() {
    Box(
        modifier = Modifier
            .width(1.dp)
            .height(40.dp)
            .background(MasterPurple.copy(alpha = 0.25f))
    )
}

@Composable
fun AvatarHeader(nombre: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Círculo avatar con iniciales
        Box(
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
                .background(MasterYellow),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text       = nombre.take(2).uppercase(),
                fontSize   = 34.sp,
                fontWeight = FontWeight.ExtraBold,
                color      = MasterPurpleDark
            )
        }

        Spacer(Modifier.height(10.dp))

        Text(
            text       = nombre,
            fontSize   = 22.sp,
            fontWeight = FontWeight.Bold,
            color      = MasterWhite
        )
        Text(
            text     = "Clínica Veterinaria Master Dog 🐾",
            fontSize = 16.sp,
            color    = MasterWhite.copy(alpha = 0.85f)
        )
    }
}
@Composable

fun ActualizarScreen(

    onNavigateToInicio:() -> Unit ={},
    onNavigateToRegistrar:() -> Unit = {},
    onNavigateToBuscar:() -> Unit = {},
    //nombreActual: String,
    //emailActual: String,
    //telefonoActual: String,
    //onGuardar: (String, String, String) -> Unit

){

    var nombre   by remember { mutableStateOf("") }
    var email    by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var error    by remember { mutableStateOf("") }
    var exito    by remember { mutableStateOf("") }
    var visible  by remember { mutableStateOf(false) }

    Scaffold(


        bottomBar = {
            NavigationBar( containerColor = MasterPurple.copy(alpha = 0.25f) ) {
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToInicio,
                    // icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") }
                    icon = { Text("🐾", fontSize = 22.sp) },
                    label = { Text("Cuida su \nsalud") },


                    )
                FooterDivider()
                NavigationBarItem(
                    selected = true,
                    onClick = onNavigateToRegistrar,
                    //icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Catalogo") }
                    icon = { Text("👨‍⚕️", fontSize = 22.sp) },
                    label = { Text("Atencion \nProfesional") }
                )

                FooterDivider()
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToBuscar,
                    //icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito") }
                    icon = { Text("📋", fontSize = 22.sp) },
                    label = { Text("Resultados \nConfiables") }


                )

            }

        }
    ){


            paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues).background(MasterPurpleLight),
            contentAlignment = Alignment.Center
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(48.dp))

                // ── Avatar + nombre de clínica ─────────────────
                AvatarHeader(nombre = nombre)

                Spacer(Modifier.height(24.dp))

                // ── Tarjeta del formulario ─────────────────────
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(12.dp, RoundedCornerShape(24.dp)),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = MasterWhite)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Actualizar Perfil",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MasterPurple
                        )

                        VetTextField(
                            value = nombre,
                            onValueChange = { nombre = it },
                            label = "Nombre completo",
                            icon = Icons.Filled.Person
                        )

                        VetTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = "Correo electrónico",
                            icon = Icons.Filled.Email
                        )

                        VetTextField(
                            value = telefono,
                            onValueChange = { telefono = it },
                            label = "Teléfono",
                            icon = Icons.Filled.Phone
                        )

                        Spacer(Modifier.height(4.dp))

                        // ── Botón guardar ──────────────────────
                        Button(
                            onClick = {
                                if (nombre.isBlank() || email.isBlank()) {
                                    error = "⚠️ Nombre y email son obligatorios"
                                    exito = ""
                                } else {
                                    error = ""
                                    exito = "✔️ Perfil actualizado correctamente"
                                    // onGuardar(nombre, email, telefono)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MasterPurple,
                                contentColor   = MasterWhite
                            ),
                            elevation = ButtonDefaults.buttonElevation(6.dp)
                        ) {
                            Text(
                                text       = "Guardar Cambios",
                                fontSize   = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        // ── Mensajes de estado ─────────────────
                        if (error.isNotEmpty()) {
                            StatusMessage(text = error, color = MasterError)
                        }
                        if (exito.isNotEmpty()) {
                            StatusMessage(text = exito, color = MasterSuccess)
                        }
                    }
                }


            }
        }


    }



}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VetTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector
) {
    OutlinedTextField(
        value         = value,
        onValueChange = onValueChange,
        label         = { Text(label) },
        leadingIcon   = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MasterTeal
            )
        },
        modifier = Modifier.fillMaxWidth(),
        shape    = RoundedCornerShape(14.dp),
        colors   = OutlinedTextFieldDefaults.colors(
            focusedBorderColor   = MasterTeal,
            unfocusedBorderColor = MasterPurple.copy(alpha = 0.4f),
            focusedLabelColor    = MasterTeal,
            unfocusedLabelColor  = MasterPurple,
            cursorColor          = MasterTeal
        )
    )
}

@Composable
fun StatusMessage(text: String, color: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape    = RoundedCornerShape(12.dp),
        colors   = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.12f)
        )
    ) {
        Text(
            text      = text,
            color     = color,
            fontSize  = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier  = Modifier.padding(12.dp)
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun InicioScreenPreview(){
    MaterialTheme() {
        ActualizarScreen(

        )
    }

}