package com.example.api_practica.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Única instancia de los clientes de red de la app (patrón singleton con `object`).
 * Un solo cliente HTTP compartido por las 4 APIs; cada API tiene su propia base url.
 * SOLID (O): agregar una API nueva es agregar una base url y una línea con [crearRetrofit].
 */
object RetrofitClient {

    // Deben terminar en "/" para que Retrofit pueda concatenar las rutas relativas.
    private const val BASE_URL_ENSEMBL = "https://rest.ensembl.org/"
    private const val BASE_URL_GBIF = "https://api.gbif.org/v1/"
    private const val BASE_URL_CROSSREF = "https://api.crossref.org/"
    private const val BASE_URL_INATURALIST = "https://api.inaturalist.org/v1/"

    private val clienteHttp: OkHttpClient = OkHttpClient.Builder()
        // Muestra cada petición en Logcat (filtrar por "OkHttp").
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
        .build()

    private fun crearRetrofit(baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(clienteHttp)
            .addConverterFactory(GsonConverterFactory.create()) // JSON <-> objetos Kotlin
            .build()

    val ensemblApi: EnsemblApiService by lazy {
        crearRetrofit(BASE_URL_ENSEMBL).create(EnsemblApiService::class.java)
    }
    val gbifApi: GbifApiService by lazy {
        crearRetrofit(BASE_URL_GBIF).create(GbifApiService::class.java)
    }
    val crossrefApi: CrossrefApiService by lazy {
        crearRetrofit(BASE_URL_CROSSREF).create(CrossrefApiService::class.java)
    }
    val iNaturalistApi: INaturalistApiService by lazy {
        crearRetrofit(BASE_URL_INATURALIST).create(INaturalistApiService::class.java)
    }
}