package com.example.api_practica.ui.articulos

import androidx.compose.runtime.Composable
import com.example.api_practica.ui.components.PantallaEnConstruccion
import com.example.api_practica.ui.theme.AcentoCrossref

@Composable
fun ArticulosScreen(onVolver: () -> Unit) {
    PantallaEnConstruccion(
        titulo = "Artículos científicos",
        subtitulo = "Crossref · publicaciones sobre un tema",
        etiqueta = "Crossref",
        colorEtiqueta = AcentoCrossref,
        onVolver = onVolver
    )
}
