package com.example.api_practica.data

import com.example.api_practica.data.remote.GbifApiService
import com.example.api_practica.model.Especie
import com.example.api_practica.util.Resultado

//La interfaz
interface GbifRepository {
    suspend fun buscarEspecies(consulta: String): Resultado<List<Especie>>
}

// a implementación remota
class GbifRepositoryRemoto(
    private val api: GbifApiService
) : GbifRepository {

    override suspend fun buscarEspecies(consulta: String): Resultado<List<Especie>> {
        return try {
            val respuesta = api.buscarEspecies(consulta)
            val lista = respuesta.especies ?: emptyList()
            Resultado.Exito(lista)
        } catch (e: Exception) {
            Resultado.Error(e.message ?: "Error desconocido")
        }
    }
}