package com.iesejemplo.llaves.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.VpnKey
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iesejemplo.llaves.ui.components.*
import com.iesejemplo.llaves.ui.theme.*

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun LoginScreen(error: String?, onLogin: (String, String) -> Unit) {
    var user by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var showForgot by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Navy)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(48.dp))

        Box(
            modifier = Modifier
                .size(96.dp)
                .background(Orange, RoundedCornerShape(28.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Outlined.VpnKey, null, tint = InkDark, modifier = Modifier.size(44.dp))
        }
        Spacer(Modifier.height(24.dp))
        Text("IES Ejemplo", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text("Gestión de llaves del centro", color = Color(0xFFD6E0F0), fontSize = 17.sp)
        Spacer(Modifier.height(48.dp))

        Card(
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(24.dp)) {
                FieldLabel("Usuario")
                AppTextField(
                    value = user, onValueChange = { user = it },
                    placeholder = "profesor@iesejemplo.es",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                Spacer(Modifier.height(20.dp))
                FieldLabel("Contraseña")
                AppTextField(
                    value = pass, onValueChange = { pass = it },
                    placeholder = "Contraseña",
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                if (error != null) {
                    Spacer(Modifier.height(10.dp))
                    Text(error, color = RedText, fontSize = 14.sp)
                }
                Spacer(Modifier.height(24.dp))
                PrimaryButton("Entrar", onClick = { onLogin(user, pass) })
            }
        }

        Spacer(Modifier.height(32.dp))
        Text(
            "¿Olvidaste tu contraseña?",
            color = Color(0xFFD6E0F0),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .clickable { showForgot = true }
                .padding(16.dp)
        )
        Spacer(Modifier.height(24.dp))
    }

    if (showForgot) {
        AlertDialog(
            onDismissRequest = { showForgot = false },
            confirmButton = { TextButton(onClick = { showForgot = false }) { Text("Entendido") } },
            title = { Text("Recuperar contraseña") },
            text = { Text("Contacta con jefatura de estudios para restablecer tu contraseña.") }
        )
    }
}
