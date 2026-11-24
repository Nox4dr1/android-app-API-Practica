package com.example.api_practica.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.api_practica.ui.theme.AzulNoche
import com.example.api_practica.ui.theme.DegradadoMarca
import com.example.api_practica.ui.theme.TextoSobreNoche

/**
 * Encabezado común de las pantallas: fondo azul noche, botón volver, título en serif,
 * etiqueta con el color de la API y la franja de degradado de la marca.
 * SOLID (S y O): un solo componente para las 4 pantallas; cada una solo cambia textos y color.
 */
@Composable
fun EncabezadoPantalla(
    titulo: String,
    subtitulo: String,
    etiqueta: String,
    colorEtiqueta: Color,
    onVolver: () -> Unit
) {
    Column(Modifier.fillMaxWidth().background(AzulNoche).statusBarsPadding()) {
        Row(
            modifier = Modifier.padding(start = 4.dp, end = 16.dp, top = 8.dp, bottom = 20.dp),
            verticalAlignment = Alignment.Top
        ) {
            IconButton(onClick = onVolver) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver al menú",
                    tint = Color.White
                )
            }
            Column(Modifier.weight(1f).padding(top = 6.dp)) {
                Text(titulo, style = MaterialTheme.typography.headlineSmall, color = Color.White)
                Text(subtitulo, style = MaterialTheme.typography.bodySmall, color = TextoSobreNoche)
            }
            Surface(
                shape = RoundedCornerShape(50),
                color = colorEtiqueta,
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Text(
                    etiqueta,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    color = Color.White,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Box(
            Modifier.fillMaxWidth().height(5.dp)
                .background(Brush.horizontalGradient(DegradadoMarca))
        )
    }
}