package com.iesejemplo.llaves.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iesejemplo.llaves.data.Loan
import com.iesejemplo.llaves.ui.components.ScreenHeader
import com.iesejemplo.llaves.ui.components.StatusChip
import com.iesejemplo.llaves.ui.theme.*

@Composable
fun LoansScreen(loans: List<Loan>, onReturn: (String) -> Unit) {
    var toReturn by remember { mutableStateOf<Loan?>(null) }

    Column(Modifier.fillMaxSize().background(Background)) {
        ScreenHeader(
            "Préstamos activos",
            if (loans.size == 1) "1 llave fuera de su sitio" else "${loans.size} llaves fuera de su sitio"
        )
        if (loans.isEmpty()) {
            Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text("No hay préstamos activos", color = InkMuted, fontSize = 16.sp)
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f).fillMaxWidth()
            ) {
                items(loans, key = { it.keyCode }) { loan ->
                    LoanCard(loan) { toReturn = loan }
                }
            }
        }
    }

    toReturn?.let { loan ->
        AlertDialog(
            onDismissRequest = { toReturn = null },
            title = { Text("Devolver llave") },
            text = { Text("¿Confirmas que ${loan.person} devuelve la ${loan.keyName.lowercase()}?") },
            confirmButton = {
                TextButton(onClick = { onReturn(loan.keyCode); toReturn = null }) { Text("Devolver") }
            },
            dismissButton = {
                TextButton(onClick = { toReturn = null }) { Text("Cancelar") }
            }
        )
    }
}

private fun initials(name: String): String =
    name.split(" ").filter { it.isNotBlank() }.take(2).joinToString("") { it.first().uppercase() }

@Composable
private fun LoanCard(loan: Loan, onReturn: () -> Unit) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, Outline),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier.size(60.dp).background(Orange, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(initials(loan.person), color = InkDark, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
            Spacer(Modifier.width(16.dp))
            Column(Modifier.weight(1f)) {
                Text(loan.keyName, color = InkDark, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(loan.person, color = InkDark, fontSize = 16.sp)
                Text(loan.since, color = InkMuted, fontSize = 14.sp)
                if (loan.overdue) {
                    Spacer(Modifier.height(8.dp))
                    StatusChip("Sin devolver", RedBg, RedText)
                }
            }
            Spacer(Modifier.width(8.dp))
            OutlinedButton(
                onClick = onReturn,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.5.dp, Navy),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Navy),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp)
            ) {
                Text("Devolver", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}
