package com.example.api_practica.model

import com.google.gson.annotations.SerializedName

// Esta es la clase de "adentro" (cada especie)
data class Especie(
    @SerializedName("key") val id: Long?,
    @SerializedName("scientificName") val nombreCientifico: String?,
    @SerializedName("rank") val rango: String?,
    @SerializedName("kingdom") val reino: String?,
    @SerializedName("class") val clase: String?,
    @SerializedName("family") val familia: String?
) {
    // Función de formateo (Regla del Paso 6: nada de require en init)
    fun nombreSeguro(): String = nombreCientifico ?: "Sin nombre"
    fun rangoSeguro(): String = rango ?: "Sin rango"
    fun reinoSeguro(): String = reino ?: "Sin reino"
    fun claseSegura(): String = clase ?: "Sin clase"
    fun familiaSegura(): String = familia ?: "Sin familia"
}