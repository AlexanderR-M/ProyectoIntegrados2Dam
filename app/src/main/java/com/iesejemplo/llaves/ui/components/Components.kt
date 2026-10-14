package com.iesejemplo.llaves.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.FormatListBulleted
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Nfc
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iesejemplo.llaves.ui.theme.*

/** Cabecera azul con esquinas inferiores redondeadas. */
@Composable
fun ScreenHeader(title: String, subtitle: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Navy, RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
            .statusBarsPadding()
            .padding(start = 20.dp, end = 20.dp, top = 28.dp, bottom = 22.dp)
    ) {
        Text(title, color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(4.dp))
        Text(subtitle, color = Color(0xFFD6E0F0), fontSize = 15.sp)
    }
}

/** Pastilla de estado (Disponible / Prestada / Sin devolver). */
@Composable
fun StatusChip(text: String, bg: Color, fg: Color) {
    Surface(color = bg, shape = RoundedCornerShape(50)) {
        Text(
            text,
            color = fg,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
        )
    }
}

@Composable
fun PrimaryButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Orange, contentColor = InkDark)
    ) {
        Text(text, fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    visualTransformation: androidx.compose.ui.text.input.VisualTransformation =
        androidx.compose.ui.text.input.VisualTransformation.None,
    keyboardOptions: androidx.compose.foundation.text.KeyboardOptions =
        androidx.compose.foundation.text.KeyboardOptions.Default,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        singleLine = singleLine,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        placeholder = { Text(placeholder, color = Color(0xFF7A7A7A)) },
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Navy,
            unfocusedBorderColor = Outline,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedTextColor = InkDark,
            unfocusedTextColor = InkDark,
        )
    )
}

@Composable
fun FieldLabel(text: String) {
    Text(text, color = InkDark, fontWeight = FontWeight.Bold, fontSize = 15.sp)
    Spacer(Modifier.height(8.dp))
}

// ---------- Barra de navegación inferior ----------
enum class Tab(val route: String, val label: String, val icon: ImageVector) {
    LLAVES("llaves", "Llaves", Icons.Outlined.GridView),
    PRESTAMOS("prestamos", "Préstamos", Icons.AutoMirrored.Outlined.FormatListBulleted),
    NFC("nfc", "NFC", Icons.Outlined.Nfc),
    INCIDENCIAS("incidencias", "Incidencias", Icons.Outlined.WarningAmber),
}

@Composable
fun AppBottomBar(currentRoute: String?, onSelect: (Tab) -> Unit, dark: Boolean = false) {
    NavigationBar(containerColor = Color.White, tonalElevation = 0.dp) {
        Tab.entries.forEach { tab ->
            val selected = currentRoute == tab.route
            NavigationBarItem(
                selected = selected,
                onClick = { onSelect(tab) },
                icon = { Icon(tab.icon, contentDescription = tab.label) },
                label = {
                    Text(
                        tab.label,
                        fontSize = 13.sp,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Navy,
                    selectedTextColor = Navy,
                    unselectedIconColor = InkMuted,
                    unselectedTextColor = InkMuted,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

val OutlinedNavyBorder = BorderStroke(1.5.dp, Navy)
