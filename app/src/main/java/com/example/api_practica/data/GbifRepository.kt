package com.example.api_practica.data

import com.example.api_practica.data.remote.GbifApiService
import com.example.api_practica.model.Especie
import com.example.api_practica.util.Resultado
import com.example.api_practica.util.llamarApi

// 1. La interfaz (El contrato)
interface GbifRepository {
    suspend fun buscarEspecies(consulta: String): Resultado<List<Especie>>
}

// 2. La implementación remota (La que realmente va a internet)
class GbifRepositoryRemoto(
    private val api: GbifApiService
) : GbifRepository {
    override suspend fun buscarEspecies(consulta: String): Resultado<List<Especie>> {
        // Reemplazamos el try/catch por llamarApi
        return llamarApi {
            val respuesta = api.buscarEspecies(consulta)
            respuesta.especies ?: emptyList()
        }
    }
}