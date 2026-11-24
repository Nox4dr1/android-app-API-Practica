package com.example.api_practica.data

import com.example.api_practica.data.remote.EnsemblApiService
import com.example.api_practica.data.remote.RetrofitClient
import com.example.api_practica.model.Gen
import com.example.api_practica.model.Secuencia
import com.example.api_practica.util.Resultado
import com.example.api_practica.util.llamarApi

/**
 * Contrato para obtener un gen y sus secuencias. El ViewModel depende de esta interfaz, no de Retrofit.
 * SOLID (D): inversión de dependencias.
 * SOLID (L): cualquier implementación (remota o falsa para pruebas) se puede usar igual.
 */
interface GenRepository {
    suspend fun obtenerGen(especie: String, simbolo: String): Resultado<Gen>
    suspend fun obtenerSecuencia(idGen: String): Resultado<List<Secuencia>>
}

/** Implementación que obtiene el gen desde la API Ensembl. */
class GenRepositoryRemoto(
    private val api: EnsemblApiService = RetrofitClient.ensemblApi
) : GenRepository {

    override suspend fun obtenerGen(especie: String, simbolo: String): Resultado<Gen> = llamarApi {
        api.obtenerGen(normalizarEspecie(especie), simbolo.trim(), FORMATO_JSON)
    }

    override suspend fun obtenerSecuencia(idGen: String): Resultado<List<Secuencia>> = llamarApi {
        api.obtenerSecuencia(idGen.trim(), TIPO_CDS, MULTIPLE_SEQ, FORMATO_JSON)
    }

    /** Ensembl espera la especie en minúsculas y con guion bajo: "Homo sapiens" -> "homo_sapiens". */
    private fun normalizarEspecie(texto: String): String =
        texto.trim().lowercase().replace(" ", "_")

    private companion object {
        const val FORMATO_JSON = "application/json"
        const val TIPO_CDS = "cds"
        const val MULTIPLE_SEQ = 1
    }
}
