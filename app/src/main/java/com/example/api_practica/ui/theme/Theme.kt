package com.example.api_practica.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Esquema de colores "Biología a tu alcance". Sin dynamic color y solo modo claro.
 * Si el dynamic color estuviera activo, en Android 12+ el sistema reemplazaría
 * estos colores por los del fondo de pantalla del celular.
 */
private val BiologiaColores = lightColorScheme(
    primary = AzulNoche,
    onPrimary = Color.White,
    primaryContainer = LavandaClaro,
    onPrimaryContainer = AzulNoche,
    secondary = AcentoCrossref,
    onSecondary = Color.White,
    tertiary = AcentoEnsembl,
    onTertiary = Color.White,
    background = FondoLavanda,
    onBackground = TextoPrincipal,
    surface = Color.White,
    onSurface = TextoPrincipal,
    onSurfaceVariant = TextoSecundario,
    outline = BordeCampo,
    outlineVariant = BordeSuave
)

@Composable
fun APIPracticaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = BiologiaColores,
        typography = Typography,
        content = content
    )
}