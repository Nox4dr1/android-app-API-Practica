package com.example.api_practica.data.remote

import com.example.api_practica.model.TaxonesRespuesta
import retrofit2.http.GET
import retrofit2.http.Query

interface INaturalistApiService {

    @GET("taxa")
    suspend fun buscarTaxones(
        @Query("q") consulta: String,
        @Query("locale") idioma: String = "es"
    ): TaxonesRespuesta

}