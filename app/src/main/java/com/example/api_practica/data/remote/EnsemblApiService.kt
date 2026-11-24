package com.example.api_practica.data.remote

import com.example.api_practica.model.Gen
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Endpoints de la API Ensembl. Retrofit genera la implementación y Gson
 * convierte el JSON directamente en el modelo [Gen].
 * SOLID (I): solo tiene los endpoints de Ensembl.
 */
interface EnsemblApiService {

    /**
     * Busca un gen por su símbolo (por ejemplo, BRCA2) dentro de una especie (homo_sapiens).
     * `formato` debe ser "application/json"; si no, Ensembl no responde en JSON.
     */
    @GET("lookup/symbol/{especie}/{simbolo}")
    suspend fun obtenerGen(
        @Path("especie") especie: String,
        @Path("simbolo") simbolo: String,
        @Query("content-type") formato: String
    ): Gen
}
