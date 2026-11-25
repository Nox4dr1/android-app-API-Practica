package com.example.api_practica.data

import com.example.api_practica.data.remote.EnsemblApiService
import com.example.api_practica.data.remote.RetrofitClient
import com.example.api_practica.model.Gen
import com.example.api_practica.util.Resultado
import com.example.api_practica.util.llamarApi
import com.example.api_practica.model.Secuencia

/**
 * Contrato para obtener un gen. El ViewModel depende de esta interfaz, no de Retrofit.
 * SOLID (D): inversión de dependencias.
 * SOLID (L): cualquier implementación (remota o falsa para pruebas) se puede usar igual.
 */
interface GenRepository {
    suspend fun obtenerGen(especie: String, simbolo: String): Resultado<Gen>
    suspend fun obtenerSecuenciaCds(idGen: String): Resultado<Secuencia>
}

/** Implementación que obtiene el gen desde la API Ensembl. */
class GenRepositoryRemoto(
    private val api: EnsemblApiService = RetrofitClient.ensemblApi
) : GenRepository {

    override suspend fun obtenerGen(especie: String, simbolo: String): Resultado<Gen> = llamarApi {
        api.obtenerGen(normalizarEspecie(especie), simbolo.trim(), FORMATO_JSON)
    }


    /** Pide el CDS de todos los transcritos del gen y devuelve el más largo. */
    override suspend fun obtenerSecuenciaCds(idGen: String): Resultado<Secuencia> {
        val resultado = llamarApi {
            api.obtenerSecuencias(idGen, TIPO_CDS, MULTIPLES_SECUENCIAS, FORMATO_JSON)
        }
        return when (resultado) {
            is Resultado.Exito -> resultado.datos.maxByOrNull { it.longitud() }
                ?.let { Resultado.Exito(it) }
                ?: Resultado.Error("Este gen no tiene secuencia codificante (CDS).")
            is Resultado.Error -> resultado
        }
    }

    /** Ensembl espera la especie en minúsculas y con guion bajo: "Homo sapiens" -> "homo_sapiens". */
    private fun normalizarEspecie(texto: String): String =
        texto.trim().lowercase().replace(" ", "_")

    private companion object {
        const val FORMATO_JSON = "application/json"
        const val TIPO_CDS = "cds"
        const val MULTIPLES_SECUENCIAS = 1
    }
}
