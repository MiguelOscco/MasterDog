@file:Suppress("SpellCheckingInspection")

package com.app.masterdog.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.masterdog.ui.theme.MasterDogTheme
import com.app.masterdog.ui.theme.MasterError
import com.app.masterdog.ui.theme.MasterPurple
import com.app.masterdog.ui.theme.MasterPurpleDark
import com.app.masterdog.ui.theme.MasterPurpleLight
import com.app.masterdog.ui.theme.MasterSuccess
import com.app.masterdog.ui.theme.MasterTeal
import com.app.masterdog.ui.theme.MasterWhite
import com.app.masterdog.ui.theme.MasterYellow

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit = {},
    onGoogleLogin: () -> Unit = {}
) {
    var registerMode by rememberSaveable { mutableStateOf(false) }
    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var message by remember { mutableStateOf<UiMessage?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(MasterPurpleDark, MasterPurple, MasterPurpleLight)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .navigationBarsPadding()
                .imePadding()
                .padding(horizontal = 22.dp, vertical = 36.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BrandHeader()
            Spacer(Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = MasterWhite)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        FilterChip(
                            selected = !registerMode,
                            onClick = {
                                registerMode = false
                                message = null
                            },
                            label = { Text("Iniciar sesión") }
                        )
                        FilterChip(
                            selected = registerMode,
                            onClick = {
                                registerMode = true
                                message = null
                            },
                            label = { Text("Crear cuenta") }
                        )
                    }

                    Text(
                        text = if (registerMode) "Registro de cliente" else "Acceso seguro",
                        color = MasterPurpleDark,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    if (registerMode) {
                        MasterTextField(
                            value = firstName,
                            onValueChange = { firstName = it },
                            label = "Nombre",
                            icon = Icons.Default.Person
                        )
                        MasterTextField(
                            value = lastName,
                            onValueChange = { lastName = it },
                            label = "Apellidos",
                            icon = Icons.Default.Person
                        )
                        MasterTextField(
                            value = phone,
                            onValueChange = { phone = it },
                            label = "Teléfono",
                            icon = Icons.Default.Phone,
                            keyboardType = KeyboardType.Phone
                        )
                    }

                    MasterTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = "Correo electrónico",
                        icon = Icons.Default.Email,
                        keyboardType = KeyboardType.Email
                    )
                    MasterTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = "Contraseña",
                        icon = Icons.Default.Lock,
                        keyboardType = KeyboardType.Password,
                        isPassword = true
                    )

                    message?.let {
                        StatusBanner(text = it.text, color = it.color)
                    }

                    Button(
                        onClick = {
                            message = validateAccess(
                                registerMode = registerMode,
                                firstName = firstName,
                                lastName = lastName,
                                phone = phone,
                                email = email,
                                password = password,
                                onSuccess = onLoginSuccess
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MasterPurple)
                    ) {
                        Text(
                            text = if (registerMode) "Crear cuenta" else "Entrar",
                            fontWeight = FontWeight.Bold
                        )
                    }

                    OutlinedButton(
                        onClick = onGoogleLogin,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Continuar con Google")
                    }

                    HorizontalDivider(color = MasterPurple.copy(alpha = 0.15f))

                    TextButton(
                        onClick = {
                            message = if (email.isBlank()) {
                                UiMessage("Ingresa tu correo para enviar el enlace.", MasterError)
                            } else {
                                UiMessage("Enlace para restablecer contraseña enviado al correo.", MasterSuccess)
                            }
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text("Olvidé mi contraseña", color = MasterTeal)
                    }
                }
            }
        }
    }
}

private fun validateAccess(
    registerMode: Boolean,
    firstName: String,
    lastName: String,
    phone: String,
    email: String,
    password: String,
    onSuccess: () -> Unit
): UiMessage {
    if (registerMode && (firstName.isBlank() || lastName.isBlank() || phone.isBlank())) {
        return UiMessage("Completa nombre, apellidos y teléfono.", MasterError)
    }
    if (email.isBlank() || !email.contains("@")) {
        return UiMessage("Ingresa un correo electrónico válido.", MasterError)
    }
    if (email.equals("registrado@masterdog.com", ignoreCase = true)) {
        return UiMessage("Este correo ya está registrado.", MasterError)
    }
    if (password.length < 8) {
        return UiMessage("La contraseña debe tener mínimo 8 caracteres.", MasterError)
    }

    return if (registerMode) {
        UiMessage("Cuenta creada. Revisa tu correo para verificarla. Bienvenido a MasterDog.", MasterSuccess)
    } else {
        onSuccess()
        UiMessage("Sesión iniciada correctamente.", MasterSuccess)
    }
}

@Composable
private fun BrandHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            modifier = Modifier.clip(CircleShape),
            color = MasterYellow
        ) {
            Icon(
                imageVector = Icons.Default.VerifiedUser,
                contentDescription = null,
                tint = MasterPurpleDark,
                modifier = Modifier.padding(18.dp)
            )
        }
        Spacer(Modifier.height(12.dp))
        Text(
            text = "MasterDog",
            color = MasterWhite,
            fontSize = 34.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Text(
            text = "Gestión segura de citas para tus mascotas",
            color = MasterWhite.copy(alpha = 0.9f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun MasterTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = { Icon(icon, contentDescription = null, tint = MasterTeal) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MasterTeal,
            unfocusedBorderColor = MasterPurple.copy(alpha = 0.35f),
            focusedLabelColor = MasterTeal,
            cursorColor = MasterTeal
        )
    )
}

@Composable
private fun StatusBanner(text: String, color: Color) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.12f))
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(12.dp),
            color = color,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp
        )
    }
}

private data class UiMessage(val text: String, val color: Color)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    MasterDogTheme(dynamicColor = false) {
        LoginScreen()
    }
}

