package com.example.api_practica.data.remote

import com.example.api_practica.model.EspeciesRespuesta
import retrofit2.http.GET
import retrofit2.http.Query

interface GbifApiService {
    @GET("species/search")
    suspend fun buscarEspecies(
        @Query("q") consulta: String,
        @Query("limit") limite: Int = 20
    ): EspeciesRespuesta
}