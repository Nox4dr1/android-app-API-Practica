package com.example.api_practica.ui.taxones

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.api_practica.model.Taxon
import com.example.api_practica.ui.components.EncabezadoPantalla
import com.example.api_practica.ui.components.MensajeError
import com.example.api_practica.ui.theme.AcentoINaturalist
import com.example.api_practica.ui.theme.FondoINaturalist
import com.example.api_practica.ui.theme.TextoSecundario
import com.example.api_practica.ui.theme.APIPracticaTheme

@Composable
fun TaxonesScreen(
    onVolver: () -> Unit,
    viewModel: TaxonViewModel = viewModel()
) {
    val estado by viewModel.estado.collectAsState()
    val error = estado.error

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        EncabezadoPantalla(
            titulo = "Fotos de especies",
            subtitulo = "iNaturalist · foto y nombre común",
            etiqueta = "iNaturalist",
            colorEtiqueta = AcentoINaturalist,
            onVolver = onVolver
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            OutlinedTextField(
                value = estado.consulta,
                onValueChange = {
                    viewModel.actualizarConsulta(it)
                },
                label = {
                    Text("Animal o planta")
                },
                placeholder = {
                    Text("Ejemplo: Jaguar")
                },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    viewModel.buscar()
                },
                enabled = !estado.cargando,
                colors = ButtonDefaults.buttonColors(
                    containerColor = AcentoINaturalist
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text("Buscar especies")
            }
        }

        when {

            estado.cargando -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = AcentoINaturalist
                    )
                }
            }

            error != null -> {
                MensajeError(
                    mensaje = error,
                    colorBoton = AcentoINaturalist,
                    onReintentar = {
                        viewModel.buscar()
                    }
                )
            }

            estado.lista.isNotEmpty() -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement =
                        Arrangement.spacedBy(12.dp)
                ) {

                    items(estado.lista) { taxon ->
                        TarjetaTaxon(taxon)
                    }
                }
            }

            else -> {
                Text(
                    text = "Busca un animal o planta para conocer sus fotografías y datos.",
                    modifier = Modifier.padding(24.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextoSecundario
                )
            }
        }
    }
}

@Composable
private fun TarjetaTaxon(taxon: Taxon) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = FondoINaturalist
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            if (!taxon.foto?.urlMediana.isNullOrBlank()) {

                AsyncImage(
                    model = taxon.foto?.urlMediana,
                    contentDescription =
                        taxon.nombreCientificoTexto(),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(RoundedCornerShape(14.dp))
                )

                taxon.foto?.autoria?.let { autoria ->
                    Text(
                        text = autoria,
                        style = MaterialTheme.typography.bodySmall,
                        color = TextoSecundario
                    )
                }

            } else {
                Text(
                    text = "Fotografía no disponible",
                    color = TextoSecundario
                )
            }

            Text(
                text = taxon.nombreComunTexto(),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = AcentoINaturalist
            )

            Text(
                text = taxon.nombreCientificoTexto(),
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodyLarge
            )

            HorizontalDivider()

            Text(
                text = "Rango: ${taxon.rangoTexto()}"
            )

            Text(
                text = "Observaciones: ${taxon.observacionesTexto()}"
            )

            Text(
                text = "ID: ${taxon.id ?: "Sin dato"}",
                color = TextoSecundario
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TaxonesScreenPreview() {
    APIPracticaTheme {
        TaxonesScreen(onVolver = {})
    }
}
