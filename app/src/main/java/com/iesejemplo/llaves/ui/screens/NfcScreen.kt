package com.iesejemplo.llaves.ui.screens

import android.app.Activity
import android.nfc.NfcAdapter
import android.nfc.Tag
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Nfc
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iesejemplo.llaves.data.KeyItem
import com.iesejemplo.llaves.data.KeyStatus
import com.iesejemplo.llaves.ui.components.AppTextField
import com.iesejemplo.llaves.ui.components.StatusChip
import com.iesejemplo.llaves.ui.theme.*

@Composable
fun NfcScreen(
    query: String,
    onQueryChange: (String) -> Unit,
    results: List<KeyItem>,
    message: String?,
    onTagRead: (String) -> Unit,
    onMessageShown: () -> Unit,
    onCancel: () -> Unit,
) {
    val context = LocalContext.current
    val adapter = remember { NfcAdapter.getDefaultAdapter(context) }
    var scanning by remember { mutableStateOf(true) }
    val snackbar = remember { SnackbarHostState() }

    // Lectura NFC activa solo mientras la pantalla está visible y escaneando
    DisposableEffect(scanning, adapter) {
        val activity = context as? Activity
        if (scanning && adapter != null && adapter.isEnabled && activity != null) {
            adapter.enableReaderMode(
                activity,
                { tag: Tag -> onTagRead(tag.id.joinToString("") { "%02X".format(it) }) },
                NfcAdapter.FLAG_READER_NFC_A or NfcAdapter.FLAG_READER_NFC_B or
                    NfcAdapter.FLAG_READER_NFC_V or NfcAdapter.FLAG_READER_NFC_F,
                null
            )
        }
        onDispose { activity?.let { adapter?.disableReaderMode(it) } }
    }

    LaunchedEffect(message) {
        if (message != null) {
            snackbar.showSnackbar(message)
            onMessageShown()
        }
    }

    Box(Modifier.fillMaxSize().background(Navy)) {
        Column(
            Modifier.fillMaxSize().statusBarsPadding().padding(horizontal = 22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(36.dp))
            Text(
                "Buscar llave", color = Color.White, fontSize = 28.sp,
                fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.Start)
            )
            Spacer(Modifier.height(20.dp))
            AppTextField(query, onQueryChange, "Buscar por nombre o aula")

            if (query.isNotBlank()) {
                Spacer(Modifier.height(12.dp))
                if (results.isEmpty()) {
                    Text("Sin resultados", color = Color(0xFFD6E0F0), fontSize = 16.sp)
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.heightIn(max = 260.dp)
                    ) {
                        items(results, key = { it.code }) { ResultRow(it) }
                    }
                }
            }

            Spacer(Modifier.weight(1f))
            if (scanning) {
                RadarAnimation()
            } else {
                Box(Modifier.size(280.dp), contentAlignment = Alignment.Center) {
                    Text("Lectura detenida", color = Color(0xFFD6E0F0), fontSize = 18.sp)
                }
            }
            Spacer(Modifier.weight(1f))

            Text(
                if (scanning) "Escaneando…" else "En pausa",
                color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            Text(
                when {
                    adapter == null -> "Este dispositivo no tiene NFC"
                    !adapter.isEnabled -> "Activa el NFC en los ajustes del móvil"
                    else -> "Acerca el móvil a la etiqueta NFC de la llave"
                },
                color = Color(0xFFD6E0F0), fontSize = 16.sp, textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(24.dp))
            OutlinedButton(
                onClick = { if (scanning) { scanning = false; onCancel() } else scanning = true },
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color.White),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                contentPadding = PaddingValues(horizontal = 48.dp, vertical = 16.dp)
            ) {
                Text(if (scanning) "Cancelar" else "Reanudar", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
            Spacer(Modifier.height(24.dp))
        }
        SnackbarHost(snackbar, Modifier.align(Alignment.BottomCenter).padding(16.dp))
    }
}

@Composable
private fun ResultRow(key: KeyItem) {
    val free = key.status == KeyStatus.DISPONIBLE
    Surface(color = Color.White, shape = RoundedCornerShape(14.dp), modifier = Modifier.fillMaxWidth()) {
        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(key.code, color = Navy, fontWeight = FontWeight.Bold, fontSize = 22.sp)
            Spacer(Modifier.width(12.dp))
            Text(key.name, color = InkMuted, fontSize = 16.sp, modifier = Modifier.weight(1f))
            StatusChip(
                if (free) "Disponible" else "Prestada",
                if (free) GreenBg else RedBg,
                if (free) GreenText else RedText
            )
        }
    }
}

/** Círculos concéntricos que laten, como en el diseño. */
@Composable
private fun RadarAnimation() {
    val transition = rememberInfiniteTransition(label = "radar")
    val pulse by transition.animateFloat(
        initialValue = 0.94f, targetValue = 1.06f,
        animationSpec = infiniteRepeatable(tween(1400, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "pulse"
    )
    Box(Modifier.size(320.dp), contentAlignment = Alignment.Center) {
        Canvas(Modifier.fillMaxSize()) {
            val c = Offset(size.width / 2, size.height / 2)
            val max = size.minDimension / 2
            drawCircle(Color.White.copy(alpha = 0.22f), max * pulse, c, style = Stroke(3f))
            drawCircle(Color.White.copy(alpha = 0.35f), max * 0.72f * pulse, c, style = Stroke(3f))
            drawCircle(Color.White.copy(alpha = 0.18f), max * 0.46f, c)
            drawCircle(Orange.copy(alpha = 0.8f), max * 0.46f, c, style = Stroke(3f))
        }
        Box(
            Modifier.size(96.dp).background(Orange, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Outlined.Nfc, null, tint = InkDark, modifier = Modifier.size(40.dp))
        }
    }
}
