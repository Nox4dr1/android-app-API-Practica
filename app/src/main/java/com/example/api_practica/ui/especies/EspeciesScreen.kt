package com.example.api_practica.ui.especies

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.api_practica.ui.components.PantallaEnConstruccion
import com.example.api_practica.ui.theme.AcentoGbif
import com.example.api_practica.ui.theme.APIPracticaTheme

@Composable
fun EspeciesScreen(onVolver: () -> Unit) {
    PantallaEnConstruccion(
        titulo = "Especies",
        subtitulo = "GBIF · clasificación de una especie",
        etiqueta = "GBIF",
        colorEtiqueta = AcentoGbif,
        onVolver = onVolver
    )
}

@Preview(showBackground = true)
@Composable
private fun EspeciesScreenPreview() {
    APIPracticaTheme {
        EspeciesScreen(onVolver = {})
    }
}
