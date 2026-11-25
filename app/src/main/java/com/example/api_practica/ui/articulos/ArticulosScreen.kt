package com.example.api_practica.ui.articulos

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.api_practica.model.Articulo
import com.example.api_practica.model.Autor
import com.example.api_practica.ui.components.EncabezadoPantalla
import com.example.api_practica.ui.components.MensajeError
import com.example.api_practica.ui.theme.APIPracticaTheme
import com.example.api_practica.ui.theme.AcentoCrossref
import com.example.api_practica.ui.theme.BordeSuave
import com.example.api_practica.ui.theme.TextoSecundario

@Composable
fun ArticulosScreen(
    onVolver: () -> Unit,
    viewModel: ArticulosViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var consulta by remember { mutableStateOf("") }
    val teclado = LocalSoftwareKeyboardController.current

    val lanzarBusqueda: () -> Unit = {
        teclado?.hide()
        viewModel.buscarArticulos(consulta)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        EncabezadoPantalla(
            titulo = "Artículos científicos",
            subtitulo = "Crossref · publicaciones sobre un tema",
            etiqueta = "Crossref",
            colorEtiqueta = AcentoCrossref,
            onVolver = onVolver
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = consulta,
                onValueChange = { consulta = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = { Text("Tema o palabra clave") },
                placeholder = { Text("Ej. machine learning, CRISPR...") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { lanzarBusqueda() })
            )
            Button(
                onClick = lanzarBusqueda,
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.cargando,
                colors = ButtonDefaults.buttonColors(containerColor = AcentoCrossref)
            ) {
                Text("Buscar artículos")
            }
        }

        when {
            uiState.cargando -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = AcentoCrossref)
                }
            }

            uiState.error != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.TopCenter
                ) {
                    MensajeError(
                        mensaje = uiState.error ?: "Error desconocido",
                        colorBoton = AcentoCrossref,
                        onReintentar = lanzarBusqueda
                    )
                }
            }

            uiState.busquedaRealizada && uiState.articulos.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "No se encontraron artículos para esa búsqueda.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextoSecundario
                    )
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(uiState.articulos, key = { it.doi ?: it.hashCode() }) { articulo ->
                        TarjetaArticulo(articulo = articulo)
                    }
                }
            }
        }
    }
}

@Composable
private fun TarjetaArticulo(articulo: Articulo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, BordeSuave)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = articulo.tituloTexto(),
                    modifier = Modifier.weight(1f).padding(end = 8.dp),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Surface(
                    shape = RoundedCornerShape(50),
                    color = AcentoCrossref.copy(alpha = 0.12f)
                ) {
                    Text(
                        text = articulo.tipoTexto(),
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = AcentoCrossref,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Text(
                text = "Autores: ${articulo.autoresTexto()}",
                style = MaterialTheme.typography.bodySmall,
                color = TextoSecundario
            )
            Text(
                text = "Revista: ${articulo.revistaTexto()}",
                style = MaterialTheme.typography.bodySmall,
                color = TextoSecundario
            )
            Text(
                text = "Editorial: ${articulo.editorialTexto()}",
                style = MaterialTheme.typography.bodySmall,
                color = TextoSecundario
            )
            Text(
                text = "DOI: ${articulo.doiTexto()}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ArticulosScreenPreview() {
    APIPracticaTheme {
        ArticulosScreen(onVolver = {})
    }
}
