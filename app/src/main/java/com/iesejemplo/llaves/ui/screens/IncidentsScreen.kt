package com.iesejemplo.llaves.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iesejemplo.llaves.data.IncidentType
import com.iesejemplo.llaves.ui.components.*
import com.iesejemplo.llaves.ui.theme.*

@Composable
fun IncidentsScreen(onSend: (String, IncidentType, String) -> Boolean) {
    var key by remember { mutableStateOf("Llave Aula 102") }
    var type by remember { mutableStateOf(IncidentType.PERDIDA) }
    var description by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var sent by remember { mutableStateOf(false) }

    Column(Modifier.fillMaxSize().background(Background)) {
        ScreenHeader("Reportar incidencia", "Avisa si una llave se ha perdido o roto")

        Column(
            Modifier.weight(1f).fillMaxWidth().verticalScroll(rememberScrollState()).imePadding().padding(16.dp)
        ) {
            FieldLabel("Llave afectada")
            AppTextField(key, { key = it }, "Nombre de la llave")

            Spacer(Modifier.height(20.dp))
            FieldLabel("Tipo de incidencia")
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                TypeButton("Perdida", type == IncidentType.PERDIDA, Modifier.weight(1f)) {
                    type = IncidentType.PERDIDA
                }
                TypeButton("Rota", type == IncidentType.ROTA, Modifier.weight(1f)) {
                    type = IncidentType.ROTA
                }
            }

            Spacer(Modifier.height(20.dp))
            FieldLabel("Descripción")
            AppTextField(
                value = description, onValueChange = { description = it },
                placeholder = "Cuéntanos qué ha pasado",
                singleLine = false,
                modifier = Modifier.heightIn(min = 170.dp)
            )

            if (error != null) {
                Spacer(Modifier.height(8.dp))
                Text(error!!, color = RedText, fontSize = 14.sp)
            }

            Spacer(Modifier.height(20.dp))
            PrimaryButton("Enviar reporte", onClick = {
                if (onSend(key, type, description)) {
                    error = null; description = ""; sent = true
                } else {
                    error = "Indica la llave y describe lo ocurrido"
                }
            })
        }
    }

    if (sent) {
        AlertDialog(
            onDismissRequest = { sent = false },
            confirmButton = { TextButton(onClick = { sent = false }) { Text("Aceptar") } },
            title = { Text("Reporte enviado") },
            text = { Text("Hemos registrado la incidencia. Gracias por avisar.") }
        )
    }
}

@Composable
private fun TypeButton(text: String, selected: Boolean, modifier: Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier.height(56.dp),
        shape = RoundedCornerShape(16.dp),
        border = if (selected) null else BorderStroke(1.dp, Outline),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) Navy else Color.White,
            contentColor = if (selected) Color.White else InkDark
        )
    ) {
        Text(text, fontWeight = FontWeight.Bold, fontSize = 17.sp)
    }
}
