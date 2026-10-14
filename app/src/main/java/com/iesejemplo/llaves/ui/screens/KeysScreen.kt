package com.iesejemplo.llaves.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iesejemplo.llaves.data.KeyItem
import com.iesejemplo.llaves.data.KeyStatus
import com.iesejemplo.llaves.ui.components.ScreenHeader
import com.iesejemplo.llaves.ui.components.StatusChip
import com.iesejemplo.llaves.ui.theme.*

@Composable
fun KeysScreen(keys: List<KeyItem>, available: Int, borrowed: Int) {
    Column(Modifier.fillMaxSize().background(Background)) {
        ScreenHeader("Llaves del centro", "Consulta el estado de cada llave")

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f).fillMaxWidth()
        ) {
            item(span = { GridItemSpan(1) }) {
                CounterCard(available, "Disponibles", GreenBg, GreenText)
            }
            item(span = { GridItemSpan(1) }) {
                CounterCard(borrowed, "Prestadas", RedBg, RedText)
            }
            items(keys, key = { it.code }) { KeyCard(it) }
        }
    }
}


@Composable
private fun CounterCard(count: Int, label: String, bg: Color, fg: Color) {
    Surface(color = bg, shape = RoundedCornerShape(20.dp), modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(horizontal = 24.dp, vertical = 18.dp)) {
            Text(count.toString(), color = fg, fontSize = 34.sp, fontWeight = FontWeight.Bold)
            Text(label, color = fg, fontSize = 17.sp)
        }
    }
}

@Composable
private fun KeyCard(key: KeyItem) {
    val free = key.status == KeyStatus.DISPONIBLE
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, Outline),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(20.dp)) {
            Text(key.code, color = Navy, fontSize = 40.sp, fontWeight = FontWeight.Bold)
            Text(key.name, color = InkMuted, fontSize = 17.sp)
            Spacer(Modifier.height(14.dp))
            StatusChip(
                text = if (free) "Disponible" else "Prestada",
                bg = if (free) GreenBg else RedBg,
                fg = if (free) GreenText else RedText
            )
        }
    }
}
