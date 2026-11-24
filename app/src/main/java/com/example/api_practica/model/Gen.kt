package com.example.api_practica.model

import com.google.gson.annotations.SerializedName

private const val SIN_DATO = "Sin dato"

/**
 * Un gen según Ensembl (capa Modelo).
 * GET https://rest.ensembl.org/lookup/symbol/homo_sapiens/BRCA2?content-type=application/json
 * Ensembl devuelve UN solo objeto (no una lista), por eso no hay clase "Respuesta".
 * Todos los campos llevan `?` porque Gson no pasa por el constructor
 * y la API puede no mandar alguno.
 * Encapsulamiento: la forma de mostrar cada dato está aquí, no en la pantalla.
 * SOLID (S): solo representa un gen y cómo se describen sus datos.
 */
data class Gen(
    @SerializedName("id") val id: String?,
    @SerializedName("display_name") val simbolo: String?,
    @SerializedName("description") val descripcion: String?,
    @SerializedName("species") val especie: String?,
    @SerializedName("biotype") val tipoBiologico: String?,
    @SerializedName("seq_region_name") val cromosoma: String?,
    @SerializedName("start") val inicio: Long?,
    @SerializedName("end") val fin: Long?,
    @SerializedName("strand") val hebra: Int?,
    @SerializedName("assembly_name") val ensamblaje: String?
) {
    fun idTexto(): String = id ?: SIN_DATO
    fun simboloTexto(): String = simbolo ?: SIN_DATO
    fun cromosomaTexto(): String = cromosoma ?: SIN_DATO
    fun ensamblajeTexto(): String = ensamblaje ?: SIN_DATO

    /** Quita la referencia final "[Source:HGNC ...]" que trae la descripción. */
    fun descripcionTexto(): String =
        descripcion?.substringBefore(" [Source")?.trim() ?: SIN_DATO

    /** "homo_sapiens" -> "Homo sapiens". */
    fun especieTexto(): String =
        especie?.replace("_", " ")?.replaceFirstChar { it.uppercase() } ?: SIN_DATO

    /** El tipo de gen en español. */
    fun tipoTexto(): String = when (tipoBiologico) {
        "protein_coding" -> "Codificante de proteína"
        "lncRNA" -> "ARN largo no codificante"
        "miRNA" -> "MicroARN"
        "pseudogene" -> "Pseudogén"
        else -> tipoBiologico ?: SIN_DATO
    }

    /** En qué hebra del ADN está el gen. */
    fun hebraTexto(): String = when (hebra) {
        1 -> "+1 (directa)"
        -1 -> "-1 (inversa)"
        else -> SIN_DATO
    }

    /** Posiciones con separador de miles: 32315474 -> "32.315.474". */
    fun inicioTexto(): String = inicio?.let { "%,d".format(it) } ?: SIN_DATO
    fun finTexto(): String = fin?.let { "%,d".format(it) } ?: SIN_DATO

    /** Longitud del gen en kilobases (kb). Null si falta el inicio o el fin. */
    fun longitudKb(): Double? =
        if (inicio != null && fin != null) (fin - inicio + 1) / 1000.0 else null

    fun longitudTexto(): String = longitudKb()?.let { "≈ %.1f kb".format(it) } ?: SIN_DATO
}