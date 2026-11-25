package com.example.api_practica.ui.genes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import com.example.api_practica.ui.components.DialogoAyuda
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.api_practica.model.Gen
import com.example.api_practica.ui.components.EncabezadoPantalla
import com.example.api_practica.ui.components.MensajeError
import com.example.api_practica.ui.theme.AcentoEnsembl
import androidx.compose.ui.tooling.preview.Preview
import com.example.api_practica.ui.theme.APIPracticaTheme
import com.example.api_practica.ui.theme.BordeSuave
import com.example.api_practica.ui.theme.FondoEnsembl
import com.example.api_practica.ui.theme.FondoLavanda
import com.example.api_practica.ui.theme.TextoSecundario
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.TextButton
import com.example.api_practica.model.Secuencia

/**
 * Pantalla de genes (capa Vista). Solo dibuja el uiState y avisa los eventos al ViewModel.
 * No sabe nada de Retrofit ni de cómo se obtienen los datos.
 */
@Composable
fun GenesScreen(
    onVolver: () -> Unit,
    viewModel: GenesViewModel = viewModel()
) {
    val estado by viewModel.uiState.collectAsState()
    var especie by rememberSaveable { mutableStateOf("homo_sapiens") }
    var simbolo by rememberSaveable { mutableStateOf("") }
    val error = estado.error
    val gen = estado.gen

    Column(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        EncabezadoPantalla(
            titulo = "Genes y ADN",
            subtitulo = "Ensembl · ubicación y secuencia",
            etiqueta = "Ensembl",
            colorEtiqueta = AcentoEnsembl,
            onVolver = onVolver
        )
        Column(
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = especie,
                    onValueChange = { especie = it },
                    label = { Text("Especie") },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = simbolo,
                    onValueChange = { simbolo = it },
                    label = { Text("Símbolo") },
                    placeholder = { Text("BRCA2") },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.width(130.dp)
                )
            }
            Button(
                onClick = { viewModel.buscar(especie, simbolo) },
                colors = ButtonDefaults.buttonColors(containerColor = AcentoEnsembl),
                modifier = Modifier.fillMaxWidth().height(52.dp)
            ) { Text("Buscar gen") }
        }
        when {
            estado.cargando -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = AcentoEnsembl)
            }
            error != null -> MensajeError(
                mensaje = error,
                colorBoton = AcentoEnsembl,
                onReintentar = { viewModel.buscar(especie, simbolo) }
            )
            gen != null -> Column(
                Modifier.verticalScroll(rememberScrollState()).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FichaGen(gen)
                SeccionSecuencia(
                    gen = gen,
                    estado = estado,
                    onVerSecuencia = { viewModel.verSecuencia() }
                )
            }
            else -> Text(
                "Escribe el símbolo de un gen, por ejemplo BRCA2 o TP53.",
                modifier = Modifier.padding(24.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = TextoSecundario
            )
        }
    }
}

/** Ficha del gen: símbolo, tipo, identificador, descripción y 6 datos de ubicación. */
@Composable
private fun FichaGen(gen: Gen) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, BordeSuave)
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(gen.simboloTexto(), style = MaterialTheme.typography.headlineLarge)
                    Surface(shape = RoundedCornerShape(50), color = FondoEnsembl) {
                        Text(
                            gen.tipoTexto(),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            color = AcentoEnsembl,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
                Text(
                    "${gen.idTexto()} · ${gen.especieTexto()}",
                    style = MaterialTheme.typography.bodySmall,
                    fontFamily = FontFamily.Monospace,
                    color = TextoSecundario
                )
                Text(gen.descripcionTexto(), style = MaterialTheme.typography.bodyLarge)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DatoGen("Cromosoma", gen.cromosomaTexto(), ayuda = GlosarioGenes.CROMOSOMA, modifier = Modifier.weight(1f))
                DatoGen("Hebra", gen.hebraTexto(), ayuda = GlosarioGenes.HEBRA, modifier = Modifier.weight(1f))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DatoGen("Inicio", gen.inicioTexto(), ayuda = GlosarioGenes.INICIO, modifier = Modifier.weight(1f))
                DatoGen("Fin", gen.finTexto(), ayuda = GlosarioGenes.FIN, modifier = Modifier.weight(1f))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DatoGen("Longitud", gen.longitudTexto(), ayuda = GlosarioGenes.LONGITUD_GEN, modifier = Modifier.weight(1f))
                DatoGen("Ensamblaje", gen.ensamblajeTexto(), ayuda = GlosarioGenes.ENSAMBLAJE, modifier = Modifier.weight(1f))
            }
        }
    }
}
/**
 * Un recuadro con una etiqueta pequeña y su valor.
 * Si tiene `ayuda`, muestra un ⓘ y al tocarlo abre una ventana con la explicación.
 */
@Composable
private fun DatoGen(
    etiqueta: String,
    valor: String,
    ayuda: String? = null,
    modifier: Modifier = Modifier
) {
    var mostrarAyuda by remember { mutableStateOf(false) }
    val forma = RoundedCornerShape(14.dp)

    Column(
        modifier = modifier
            .clip(forma)
            .background(FondoLavanda, forma)
            .then(
                if (ayuda != null) {
                    Modifier.clickable(onClickLabel = "Ver qué significa $etiqueta") { mostrarAyuda = true }
                } else {
                    Modifier
                }
            )
            .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(etiqueta, style = MaterialTheme.typography.labelSmall, color = TextoSecundario)
            if (ayuda != null) {
                Icon(
                    Icons.Outlined.Info,
                    contentDescription = null,
                    tint = TextoSecundario,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
        Text(valor, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
    }

    if (mostrarAyuda && ayuda != null) {
        DialogoAyuda(
            titulo = etiqueta,
            explicacion = ayuda,
            colorAcento = AcentoEnsembl,
            onCerrar = { mostrarAyuda = false }
        )
    }
}


/**
 * Debajo de la ficha: el botón para pedir la secuencia, el indicador de carga,
 * o la tarjeta con la secuencia si ya llegó.
 */
@Composable
private fun SeccionSecuencia(gen: Gen, estado: GenesUiState, onVerSecuencia: () -> Unit) {
    val secuencia = estado.secuencia
    val errorSecuencia = estado.errorSecuencia
    when {
        !gen.tieneSecuenciaCodificante() -> Text(
            "Este gen no codifica proteína, así que no tiene secuencia CDS.",
            style = MaterialTheme.typography.bodySmall,
            color = TextoSecundario
        )
        estado.cargandoSecuencia -> Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = AcentoEnsembl)
        }
        secuencia != null -> TarjetaSecuencia(secuencia)
        else -> Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            if (errorSecuencia != null) {
                Text(errorSecuencia, style = MaterialTheme.typography.bodySmall, color = AcentoEnsembl)
            }
            OutlinedButton(
                onClick = onVerSecuencia,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                border = BorderStroke(1.5.dp, AcentoEnsembl)
            ) { Text("Ver secuencia (CDS)", color = AcentoEnsembl) }
        }
    }
}

/** Tarjeta con 4 datos de la secuencia y sus bases en bloques de 10. */
@Composable
private fun TarjetaSecuencia(secuencia: Secuencia) {
    var mostrarCompleta by rememberSaveable { mutableStateOf(false) }
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, BordeSuave)
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    "Secuencia codificante (CDS)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    "Transcrito ${secuencia.idTexto()}",
                    style = MaterialTheme.typography.bodySmall,
                    fontFamily = FontFamily.Monospace,
                    color = TextoSecundario
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DatoGen("Longitud", secuencia.longitudTexto(), ayuda = GlosarioGenes.LONGITUD_CDS, modifier = Modifier.weight(1f))
                DatoGen("Contenido GC", secuencia.porcentajeGCTexto(), ayuda = GlosarioGenes.CONTENIDO_GC, modifier = Modifier.weight(1f))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DatoGen("Codones", secuencia.codonesTexto(), ayuda = GlosarioGenes.CODONES, modifier = Modifier.weight(1f))
                DatoGen("Inicio · fin", secuencia.inicioFinTexto(), ayuda = GlosarioGenes.INICIO_FIN, modifier = Modifier.weight(1f))
            }
            SelectionContainer {
                Text(
                    secuencia.basesEnBloques(if (mostrarCompleta) null else BASES_VISTA_PREVIA),
                    style = MaterialTheme.typography.bodySmall,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(FondoLavanda, RoundedCornerShape(12.dp))
                        .padding(12.dp)
                )
            }
            if (secuencia.longitud() > BASES_VISTA_PREVIA) {
                TextButton(onClick = { mostrarCompleta = !mostrarCompleta }) {
                    Text(
                        if (mostrarCompleta) "Ver menos"
                        else "Ver completa (${secuencia.longitudTexto()})",
                        color = AcentoEnsembl
                    )
                }
            }
        }
    }
}

/** Cuántas bases se muestran antes de tocar "Ver completa". */
private const val BASES_VISTA_PREVIA = 300

@Preview(showBackground = true)
@Composable
private fun GenesScreenPreview() {
    APIPracticaTheme {
        GenesScreen(onVolver = {})
    }
}
