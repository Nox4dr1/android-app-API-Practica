package com.example.api_practica.ui.genes

import androidx.compose.runtime.Composable
import com.example.api_practica.ui.components.PantallaEnConstruccion
import com.example.api_practica.ui.theme.AcentoEnsembl

@Composable
fun GenesScreen(onVolver: () -> Unit) {
    PantallaEnConstruccion(
        titulo = "Genes y ADN",
        subtitulo = "Ensembl · ubicación de un gen",
        etiqueta = "Ensembl",
        colorEtiqueta = AcentoEnsembl,
        onVolver = onVolver
    )
}