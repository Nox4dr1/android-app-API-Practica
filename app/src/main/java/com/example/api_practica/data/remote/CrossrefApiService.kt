package com.example.api_practica.data.remote

import com.example.api_practica.model.ArticulosRespuesta
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Endpoints de la API Crossref. Retrofit genera la implementación y Gson
 * convierte el JSON directamente en [ArticulosRespuesta].
 * SOLID (I): solo tiene los endpoints de Crossref.
 */
interface CrossrefApiService {

    /**
     * Busca artículos académicos en Crossref según una consulta (`query`).
     * `filas` controla cuántos resultados devuelve por página.
     */
    @GET("works")
    suspend fun buscarArticulos(
        @Query("query") consulta: String,
        @Query("rows") filas: Int = 20
    ): ArticulosRespuesta
}
