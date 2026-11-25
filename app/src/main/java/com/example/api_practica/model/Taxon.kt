package com.example.api_practica.model

import com.google.gson.annotations.SerializedName

data class Taxon(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("name")
    val nombreCientifico: String?,

    @SerializedName("preferred_common_name")
    val nombreComun: String?,

    @SerializedName("rank")
    val rango: String?,

    @SerializedName("observations_count")
    val observaciones: Int?,

    @SerializedName("wikipedia_url")
    val urlWikipedia: String?,

    @SerializedName("default_photo")
    val foto: Foto?
) {
    fun nombreCientificoTexto(): String =
        nombreCientifico ?: "Sin dato"

    fun nombreComunTexto(): String =
        nombreComun ?: "Sin dato"

    fun rangoTexto(): String =
        rango ?: "Sin dato"

    fun observacionesTexto(): String =
        observaciones?.toString() ?: "Sin dato"
}

data class Foto(
    @SerializedName("medium_url")
    val urlMediana: String?,

    @SerializedName("attribution")
    val autoria: String?
)