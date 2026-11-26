package com.example.api_practica.ui.especies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.api_practica.data.GbifRepositoryRemoto
import com.example.api_practica.data.remote.RetrofitClient
import com.example.api_practica.model.Especie
import com.example.api_practica.ui.components.EncabezadoPantalla
import com.example.api_practica.ui.components.MensajeError // <-- IMPORTANTE: Agregado para el error
import com.example.api_practica.ui.theme.AcentoGbif

@Composable
fun EspeciesScreen(
    onVolver: () -> Unit,
    viewModel: EspeciesViewModel = viewModel(
        factory = EspeciesViewModelFactory(
            GbifRepositoryRemoto(RetrofitClient.gbifApi)
        )
    )
) {
    val estado by viewModel.estado.collectAsState()
    var textoBusqueda by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background) // <-- Cambiado según corrección 4
    ) {
        // El encabezado
        EncabezadoPantalla(
            titulo = "Especies",
            subtitulo = "GBIF · clasificación de una especie",
            etiqueta = "GBIF",
            colorEtiqueta = AcentoGbif,
            onVolver = onVolver
        )

        // Barra de búsqueda
        TextField(
            value = textoBusqueda,
            onValueChange = { textoBusqueda = it },
            placeholder = { Text("Buscar especie (ej. Puma concolor)") },
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        Button(
            onClick = { viewModel.buscar(textoBusqueda) },
            modifier = Modifier.padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AcentoGbif)
        ) {
            Text("Buscar")
        }

        // Manejo de estados: Cargando / Error / Lista
        when {
            estado.cargando -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = AcentoGbif)
                }
            }
            estado.error != null -> {
                // Usamos el componente MensajeError según corrección 2
                MensajeError(
                    mensaje = estado.error ?: "Error desconocido",
                    onReintentar = { viewModel.buscar(textoBusqueda) },
                    colorBoton = AcentoGbif // <-- Agregado para solucionar el error de parámetro
                )
            }
            estado.lista.isNotEmpty() -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(estado.lista) { especie ->
                        TarjetaEspecie(especie)
                    }
                }
            }
            // Texto inicial según corrección 5
            else -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Busca una especie, por ejemplo Puma concolor",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

// La tarjeta para cada elemento
@Composable
fun TarjetaEspecie(especie: Especie) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = especie.nombreSeguro(),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(text = "Rango: ${especie.rangoSeguro()}", fontSize = 14.sp)
            Text(text = "Reino: ${especie.reinoSeguro()}", fontSize = 14.sp)
            Text(text = "Clase: ${especie.claseSegura()}", fontSize = 14.sp)
            Text(text = "Familia: ${especie.familiaSegura()}", fontSize = 14.sp)
        }
    }
}