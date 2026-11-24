package com.example.api_practica.ui.menu

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import com.example.api_practica.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Biotech
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.api_practica.ui.theme.AzulNoche
import com.example.api_practica.ui.theme.AzulVivo
import com.example.api_practica.ui.theme.BordeSuave
import com.example.api_practica.ui.theme.DegradadoMarca
import com.example.api_practica.ui.theme.Magenta
import com.example.api_practica.ui.theme.Morado
import com.example.api_practica.ui.theme.Naranja
import com.example.api_practica.ui.theme.TextoSecundario
import com.example.api_practica.ui.theme.TextoSobreNoche
import androidx.compose.ui.tooling.preview.Preview
import com.example.api_practica.ui.theme.APIPracticaTheme

/**
 * Menú principal: encabezado con logo y nombre de la app, y una tarjeta por API.
 * Solo avisa el clic; no navega por sí mismo (eso lo decide AppNavigation).
 */
@Composable
fun MenuScreen(
    onIrAEspecies: () -> Unit,
    onIrAArticulos: () -> Unit,
    onIrAGenes: () -> Unit,
    onIrATaxones: () -> Unit
) {
    Column(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        EncabezadoMenu()
        Text(
            "EXPLORA",
            modifier = Modifier.padding(start = 20.dp, top = 20.dp, bottom = 10.dp),
            style = MaterialTheme.typography.labelLarge,
            color = TextoSecundario
        )
        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OpcionMenu("Especies", "GBIF · clasificación de una especie", Icons.Filled.Eco, Naranja, onIrAEspecies)
            OpcionMenu("Artículos científicos", "Crossref · publicaciones sobre un tema", Icons.AutoMirrored.Filled.Article, AzulVivo, onIrAArticulos)
            OpcionMenu("Genes y ADN", "Ensembl · ubicación de un gen", Icons.Filled.Biotech, Magenta, onIrAGenes)
            OpcionMenu("Fotos de especies", "iNaturalist · foto y nombre común", Icons.Filled.PhotoCamera, Morado, onIrATaxones)
        }
    }
}

/** Encabezado azul noche con el logo, el nombre y la franja de degradado. */
@Composable
private fun EncabezadoMenu() {
    Column(
        modifier = Modifier.fillMaxWidth().background(AzulNoche).statusBarsPadding()
            .padding(start = 20.dp, end = 20.dp, top = 28.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_logo),
                contentDescription = "Logo: hélice de ADN que crece como un árbol",
                modifier = Modifier.size(width = 62.dp, height = 72.dp)
            )
            Text(
                stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White
            )
        }
        Text(
            "Una app para investigar especies y ADN desde tu celular.",
            style = MaterialTheme.typography.bodyMedium,
            color = TextoSobreNoche
        )
    }
    Box(
        Modifier.fillMaxWidth().height(5.dp)
            .background(Brush.horizontalGradient(DegradadoMarca))
    )
}

/** Tarjeta de una API: ícono en un cuadro de color, título, subtítulo y flecha. */
@Composable
private fun OpcionMenu(
    titulo: String,
    subtitulo: String,
    icono: ImageVector,
    color: Color,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, BordeSuave)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier.size(52.dp).background(color, RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icono, contentDescription = null, tint = Color.White)
            }
            Column(Modifier.weight(1f)) {
                Text(titulo, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text(subtitulo, style = MaterialTheme.typography.bodySmall, color = TextoSecundario)
            }
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = TextoSecundario
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MenuScreenPreview() {
    APIPracticaTheme {
        MenuScreen(
            onIrAEspecies = {},
            onIrAArticulos = {},
            onIrAGenes = {},
            onIrATaxones = {}
        )
    }
}
