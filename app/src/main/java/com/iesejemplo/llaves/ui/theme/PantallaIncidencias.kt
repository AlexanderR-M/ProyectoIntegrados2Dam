
package com.iesejemplo.llaves.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

// Colores de la pantalla
private val Azul = Color(0xFF193F76)
private val Fondo = Color(0xFFF2F5FB)
private val Naranja = Color(0xFFFA8C24)
private val Texto = Color(0xFF142238)
private val Borde = Color(0xFFC4D3E7)

@Composable
fun IncidentsScreen(
    onSend: (String, String, String) -> Unit
) {

    // Variables de estado
    var llave by remember {
        mutableStateOf("Llave Aula 102")
    }

    var tipo by remember {
        mutableStateOf("Perdida")
    }

    var descripcion by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Fondo)
            .verticalScroll(rememberScrollState())
    ) {

        // CABECERA AZUL
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Azul,
                    shape = RoundedCornerShape(
                        bottomStart = 24.dp,
                        bottomEnd = 24.dp
                    )
                )
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 48.dp,
                    bottom = 18.dp
                )
        ) {

            Text(
                text = "Reportar incidencia",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Avisa si una llave se ha perdido o roto",
                fontSize = 16.sp,
                color = Color(0xFFD2DDED)
            )
        }

        // CONTENIDO DE LA PANTALLA
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // LLAVE AFECTADA
            Text(
                text = "Llave afectada",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Texto
            )

            OutlinedTextField(
                value = llave,
                onValueChange = { llave = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Borde,
                    unfocusedBorderColor = Borde,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            // TIPO DE INCIDENCIA
            Text(
                text = "Tipo de incidencia",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Texto
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                // BOTÓN PERDIDA
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp)
                        .background(
                            color = if (tipo == "Perdida")
                                Azul else Color.White,
                            shape = RoundedCornerShape(14.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = if (tipo == "Perdida")
                                Azul else Borde,
                            shape = RoundedCornerShape(14.dp)
                        )
                        .clickable {
                            tipo = "Perdida"
                        },
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "Perdida",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (tipo == "Perdida")
                            Color.White else Texto
                    )
                }

                // BOTÓN ROTA
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp)
                        .background(
                            color = if (tipo == "Rota")
                                Azul else Color.White,
                            shape = RoundedCornerShape(14.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = if (tipo == "Rota")
                                Azul else Borde,
                            shape = RoundedCornerShape(14.dp)
                        )
                        .clickable {
                            tipo = "Rota"
                        },
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "Rota",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (tipo == "Rota")
                            Color.White else Texto
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // DESCRIPCIÓN
            Text(
                text = "Descripción",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Texto
            )

            OutlinedTextField(
                value = descripcion,
                onValueChange = {
                    descripcion = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                placeholder = {
                    Text(
                        text = "Cuéntanos qué ha pasado"
                    )
                },
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Borde,
                    unfocusedBorderColor = Borde,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            // BOTÓN ENVIAR REPORTE
            Button(
                onClick = {
                    if (llave.isNotBlank() &&
                        descripcion.isNotBlank()
                    ) {
                        onSend(
                            llave,
                            tipo,
                            descripcion
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Naranja
                )
            ) {

                Text(
                    text = "Enviar reporte",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = Texto
                )
            }
        }
    }
}

// VISTA
@Preview(showBackground = true)
@Composable
fun PreviewIncidentsScreen() {
    IncidentsScreen(
        onSend = { _, _, _ -> }
    )
}
