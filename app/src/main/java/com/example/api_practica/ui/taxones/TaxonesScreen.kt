package com.example.api_practica.ui.taxones

import androidx.compose.runtime.Composable
import com.example.api_practica.ui.components.PantallaEnConstruccion
import com.example.api_practica.ui.theme.AcentoINaturalist

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
