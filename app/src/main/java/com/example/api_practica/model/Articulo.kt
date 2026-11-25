package com.example.api_practica.model

import com.google.gson.annotations.SerializedName

private const val SIN_DATO = "Sin dato"

data class Autor(
    @SerializedName("given") val nombre: String?,
    @SerializedName("family") val apellido: String?
) {
    fun nombreCompleto(): String {
        val partes = listOfNotNull(nombre?.trim(), apellido?.trim()).filter { it.isNotEmpty() }
        return if (partes.isEmpty()) SIN_DATO else partes.joinToString(" ")
    }
}

data class Articulo(
    @SerializedName("DOI") val doi: String?,
    @SerializedName("title") val titulos: List<String>?,
    @SerializedName("publisher") val editorial: String?,
    @SerializedName("type") val tipo: String?,
    @SerializedName("container-title") val revistas: List<String>?,
    @SerializedName("author") val autores: List<Autor>?
) {
    fun tituloTexto(): String =
        titulos?.firstOrNull()?.takeIf { it.isNotBlank() } ?: SIN_DATO

    fun doiTexto(): String = doi?.takeIf { it.isNotBlank() } ?: SIN_DATO

    fun editorialTexto(): String = editorial?.takeIf { it.isNotBlank() } ?: SIN_DATO

    fun revistaTexto(): String =
        revistas?.firstOrNull()?.takeIf { it.isNotBlank() } ?: SIN_DATO

    fun tipoTexto(): String = when (tipo) {
        "journal-article" -> "Artículo de revista"
        "proceedings-article" -> "Artículo de congreso"
        "book-chapter" -> "Capítulo de libro"
        "book" -> "Libro"
        "posted-content" -> "Preprint"
        else -> tipo?.takeIf { it.isNotBlank() } ?: SIN_DATO
    }

    fun autoresTexto(): String {
        val lista = autores?.map { it.nombreCompleto() }?.filter { it != SIN_DATO }.orEmpty()
        return if (lista.isEmpty()) SIN_DATO else lista.joinToString(", ")
    }
}
data class MensajeCrossref(
    @SerializedName("items") val articulos: List<Articulo>?
)

data class ArticulosRespuesta(
    @SerializedName("message") val mensaje: MensajeCrossref?
)
