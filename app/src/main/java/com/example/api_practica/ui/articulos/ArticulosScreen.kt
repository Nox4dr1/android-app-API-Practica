package com.example.api_practica.ui.articulos

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.api_practica.ui.components.PantallaEnConstruccion
import com.example.api_practica.ui.theme.AcentoCrossref
import com.example.api_practica.ui.theme.APIPracticaTheme

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

@Preview(showBackground = true)
@Composable
private fun ArticulosScreenPreview() {
    APIPracticaTheme {
        ArticulosScreen(onVolver = {})
    }
}
