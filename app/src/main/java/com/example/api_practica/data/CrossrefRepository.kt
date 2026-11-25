package com.example.api_practica.data

import com.example.api_practica.data.remote.CrossrefApiService
import com.example.api_practica.data.remote.RetrofitClient
import com.example.api_practica.model.Articulo
import com.example.api_practica.util.Resultado
import com.example.api_practica.util.llamarApi

/**
 * Contrato para buscar artículos en Crossref. El ViewModel depende de esta interfaz.
 * SOLID (D): inversión de dependencias.
 */
interface CrossrefRepository {
    suspend fun buscarArticulos(consulta: String): Resultado<List<Articulo>>
}

/** Implementación que consulta la API pública de Crossref mediante Retrofit. */
class CrossrefRepositoryRemoto(
    private val api: CrossrefApiService = RetrofitClient.crossrefApi
) : CrossrefRepository {

    override suspend fun buscarArticulos(consulta: String): Resultado<List<Articulo>> {
        val consultaLimpia = consulta.trim()
        if (consultaLimpia.isEmpty()) {
            return Resultado.Error("Escribe un término o tema para buscar artículos.")
        }

        val resultado = llamarApi {
            api.buscarArticulos(consulta = consultaLimpia, filas = FILAS_POR_DEFECTO)
        }

        return when (resultado) {
            is Resultado.Exito -> Resultado.Exito(resultado.datos.mensaje?.articulos.orEmpty())
            is Resultado.Error -> resultado
        }
    }

    private companion object {
        const val FILAS_POR_DEFECTO = 20
    }
}
