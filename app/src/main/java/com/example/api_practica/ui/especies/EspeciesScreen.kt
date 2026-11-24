package com.example.api_practica.ui.especies

import androidx.compose.runtime.Composable
import com.example.api_practica.ui.components.PantallaEnConstruccion
import com.example.api_practica.ui.theme.AcentoGbif

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
