package com.example.api_practica.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Ventana con la explicación de un término (por ejemplo, "Ensamblaje").
 * Solo dibuja; quien la usa decide cuándo mostrarla y cuándo cerrarla.
 * SOLID (S y O): un solo componente que cualquier pantalla puede reutilizar.
 */
@Composable
fun DialogoAyuda(
    titulo: String,
    explicacion: String,
    colorAcento: Color,
    onCerrar: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCerrar,
        icon = { Icon(Icons.Outlined.Info, contentDescription = null, tint = colorAcento) },
        title = { Text(titulo) },
        text = { Text(explicacion) },
        confirmButton = {
            TextButton(onClick = onCerrar) { Text("Entendido", color = colorAcento) }
        },
        containerColor = Color.White
    )
}