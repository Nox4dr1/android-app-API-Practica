package com.example.api_practica.ui.taxones

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_practica.data.TaxonRepository
import com.example.api_practica.data.TaxonRepositoryRemoto
import com.example.api_practica.model.Taxon
import com.example.api_practica.util.Resultado
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TaxonUiState(
    val consulta: String = "",
    val lista: List<Taxon> = emptyList(),
    val cargando: Boolean = false,
    val error: String? = null
)

class TaxonViewModel(
    private val repositorio: TaxonRepository =
        TaxonRepositoryRemoto()
) : ViewModel() {

    private val _estado = MutableStateFlow(TaxonUiState())

    val estado: StateFlow<TaxonUiState> =
        _estado.asStateFlow()

    fun actualizarConsulta(consulta: String) {
        _estado.update {
            it.copy(consulta = consulta)
        }
    }

    fun buscar() {
        val consulta = _estado.value.consulta.trim()

        if (consulta.isBlank()) {
            _estado.update {
                it.copy(error = "Escribe un animal o planta.")
            }
            return
        }

        viewModelScope.launch {
            _estado.update {
                it.copy(
                    cargando = true,
                    error = null,
                    lista = emptyList()
                )
            }

            when (val resultado = repositorio.buscarTaxones(consulta)) {

                is Resultado.Exito -> {
                    _estado.update {
                        it.copy(
                            cargando = false,
                            lista = resultado.datos
                        )
                    }
                }

                is Resultado.Error -> {
                    _estado.update {
                        it.copy(
                            cargando = false,
                            error = resultado.mensaje
                        )
                    }
                }
            }
        }
    }
}
