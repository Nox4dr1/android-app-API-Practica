package com.example.api_practica.ui.genes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_practica.data.GenRepository
import com.example.api_practica.data.GenRepositoryRemoto
import com.example.api_practica.model.Gen
import com.example.api_practica.model.Secuencia
import com.example.api_practica.util.Resultado
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Todo lo que la pantalla de genes necesita para dibujarse.
 * `gen` es null mientras no se haya buscado nada o si hubo error.
 * La secuencia tiene su propio "cargando" y su propio error, para que
 * si falla la secuencia la ficha del gen siga visible.
 */
data class GenesUiState(
    val gen: Gen? = null,
    val cargando: Boolean = false,
    val error: String? = null,
    val secuencia: Secuencia? = null,
    val cargandoSecuencia: Boolean = false,
    val errorSecuencia: String? = null
)

/**
 * Guarda el estado de la pantalla de genes y sobrevive a la rotación.
 * Recibe los eventos de la vista (buscar, ver secuencia) y decide qué hacer.
 * Encapsulamiento: el estado mutable es privado; hacia afuera solo se expone de lectura.
 * SOLID (D): depende de la interfaz [GenRepository], no de Retrofit.
 */
class GenesViewModel(
    private val repositorio: GenRepository = GenRepositoryRemoto()
) : ViewModel() {

    private val _uiState = MutableStateFlow(GenesUiState())
    val uiState: StateFlow<GenesUiState> = _uiState.asStateFlow()

    /** Evento de la vista: el usuario tocó "Buscar gen". Si no escribe especie, se busca en humano. */
    fun buscar(especie: String, simbolo: String) {
        if (simbolo.isBlank()) {
            _uiState.update { it.copy(error = "Escribe el símbolo del gen") }
            return
        }
        val especieBuscada = especie.ifBlank { ESPECIE_POR_DEFECTO }
        viewModelScope.launch {
            // Al buscar un gen nuevo se borra la secuencia del gen anterior.
            _uiState.update {
                it.copy(cargando = true, error = null, secuencia = null, errorSecuencia = null)
            }
            when (val resultado = repositorio.obtenerGen(especieBuscada, simbolo)) {
                is Resultado.Exito -> _uiState.update {
                    it.copy(gen = resultado.datos, cargando = false)
                }
                is Resultado.Error -> _uiState.update {
                    it.copy(gen = null, error = resultado.mensaje, cargando = false)
                }
            }
        }
    }

    /** Evento de la vista: el usuario tocó "Ver secuencia (CDS)". */
    fun verSecuencia() {
        val idGen = _uiState.value.gen?.id ?: return
        viewModelScope.launch {
            _uiState.update { it.copy(cargandoSecuencia = true, errorSecuencia = null) }
            when (val resultado = repositorio.obtenerSecuenciaCds(idGen)) {
                is Resultado.Exito -> _uiState.update {
                    it.copy(secuencia = resultado.datos, cargandoSecuencia = false)
                }
                is Resultado.Error -> _uiState.update {
                    it.copy(errorSecuencia = resultado.mensaje, cargandoSecuencia = false)
                }
            }
        }
    }

    private companion object {
        const val ESPECIE_POR_DEFECTO = "homo_sapiens"
    }
}