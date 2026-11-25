package com.example.api_practica.ui.articulos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_practica.data.CrossrefRepository
import com.example.api_practica.data.CrossrefRepositoryRemoto
import com.example.api_practica.model.Articulo
import com.example.api_practica.util.Resultado
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Todo lo que la pantalla de artículos necesita para dibujarse.
 * `articulos` guarda los resultados de la última búsqueda exitosa.
 */
data class ArticulosUiState(
    val articulos: List<Articulo> = emptyList(),
    val cargando: Boolean = false,
    val error: String? = null,
    val busquedaRealizada: Boolean = false
)

/**
 * Guarda el estado de la pantalla de artículos y sobrevive a la rotación.
 * Recibe los eventos de la vista y decide qué hacer.
 * Encapsulamiento: el estado mutable es privado; hacia afuera solo se expone de lectura.
 * SOLID (D): depende de la interfaz [CrossrefRepository], no de Retrofit.
 */
class ArticulosViewModel(
    private val repositorio: CrossrefRepository = CrossrefRepositoryRemoto()
) : ViewModel() {

    private val _uiState = MutableStateFlow(ArticulosUiState())
    val uiState: StateFlow<ArticulosUiState> = _uiState.asStateFlow()

    /** Evento de la vista: el usuario tocó "Buscar artículos". */
    fun buscarArticulos(consulta: String) {
        if (consulta.isBlank()) {
            _uiState.update { it.copy(error = "Escribe un término o tema para buscar artículos") }
            return
        }
        viewModelScope.launch {
            _uiState.update {
                it.copy(cargando = true, error = null, busquedaRealizada = true)
            }
            when (val resultado = repositorio.buscarArticulos(consulta)) {
                is Resultado.Exito -> _uiState.update {
                    it.copy(articulos = resultado.datos, cargando = false, error = null)
                }
                is Resultado.Error -> _uiState.update {
                    it.copy(articulos = emptyList(), cargando = false, error = resultado.mensaje)
                }
            }
        }
    }
}
