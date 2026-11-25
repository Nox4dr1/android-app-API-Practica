package com.example.api_practica.ui.especies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.api_practica.data.GbifRepository
import com.example.api_practica.data.GbifRepositoryRemoto
import com.example.api_practica.data.remote.RetrofitClient
import com.example.api_practica.model.Especie
import com.example.api_practica.util.Resultado
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// 1. El Estado de la UI
data class EspeciesUiState(
    val lista: List<Especie> = emptyList(),
    val cargando: Boolean = false,
    val error: String? = null
)

// 2. El ViewModel (Constructor con repositorio, como lo tenías)
class EspeciesViewModel(
    private val repositorio: GbifRepository
) : ViewModel() {

    private val _estado = MutableStateFlow(EspeciesUiState())
    val estado: StateFlow<EspeciesUiState> = _estado.asStateFlow()

    fun buscar(consulta: String) {
        viewModelScope.launch {
            _estado.value = _estado.value.copy(cargando = true, error = null)

            when (val resultado = repositorio.buscarEspecies(consulta)) {
                is Resultado.Exito -> {
                    _estado.value = _estado.value.copy(
                        lista = resultado.datos, // <--- 'datos' en plural
                        cargando = false
                    )
                }
                is Resultado.Error -> {
                    _estado.value = _estado.value.copy(
                        error = resultado.mensaje,
                        cargando = false
                    )
                }
            }
        }
    }
}

// 3. La Fábrica (Factory) que soluciona el crash
class EspeciesViewModelFactory(
    private val repositorio: GbifRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EspeciesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EspeciesViewModel(repositorio) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}