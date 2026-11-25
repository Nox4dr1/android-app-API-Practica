package com.example.api_practica.data

import com.example.api_practica.data.remote.INaturalistApiService
import com.example.api_practica.data.remote.RetrofitClient
import com.example.api_practica.model.Taxon
import com.example.api_practica.util.Resultado
import com.example.api_practica.util.llamarApi

interface TaxonRepository {

    suspend fun buscarTaxones(
        consulta: String
    ): Resultado<List<Taxon>>

}

class TaxonRepositoryRemoto(
    private val api: INaturalistApiService =
        RetrofitClient.iNaturalistApi
) : TaxonRepository {

    override suspend fun buscarTaxones(
        consulta: String
    ): Resultado<List<Taxon>> = llamarApi {

        api.buscarTaxones(
            consulta.trim(),
            "es"
        ).taxones ?: emptyList()

    }
}
