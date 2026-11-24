package com.example.api_practica.ui.taxones

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.api_practica.ui.components.PantallaEnConstruccion
import com.example.api_practica.ui.theme.AcentoINaturalist
import com.example.api_practica.ui.theme.APIPracticaTheme

@Composable
fun TaxonesScreen(onVolver: () -> Unit) {
    PantallaEnConstruccion(
        titulo = "Fotos de especies",
        subtitulo = "iNaturalist · foto y nombre común",
        etiqueta = "iNaturalist",
        colorEtiqueta = AcentoINaturalist,
        onVolver = onVolver
    )
}

@Preview(showBackground = true)
@Composable
private fun TaxonesScreenPreview() {
    APIPracticaTheme {
        TaxonesScreen(onVolver = {})
    }
}
