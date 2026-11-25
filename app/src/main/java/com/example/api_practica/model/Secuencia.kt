package com.example.api_practica.model

import com.google.gson.annotations.SerializedName

/**
 * Una secuencia de ADN según Ensembl (capa Modelo).
 * GET https://rest.ensembl.org/sequence/id/ENSG00000139618?type=cds&multiple_sequences=1&content-type=application/json
 * Cada elemento de la lista corresponde a un transcrito del gen.
 * Encapsulamiento: los cálculos sobre la secuencia están aquí, no en la pantalla.
 * SOLID (S): solo representa una secuencia y lo que se puede calcular de ella.
 */
data class Secuencia(
    @SerializedName("id") val idTranscrito: String?,
    @SerializedName("seq") val bases: String?,
    @SerializedName("molecule") val molecula: String?
) {
    private val texto: String get() = bases?.uppercase() ?: ""

    /** Cantidad de bases (pb). */
    fun longitud(): Int = texto.length

    fun longitudTexto(): String = "%,d pb".format(longitud())

    /** Porcentaje de G + C de toda la secuencia. (En cebadores se busca entre 40 y 60 %). */
    fun porcentajeGC(): Double =
        if (texto.isEmpty()) 0.0
        else texto.count { it == 'G' || it == 'C' } * 100.0 / texto.length

    fun porcentajeGCTexto(): String = "%.1f %%".format(porcentajeGC())

    /** Cuántos codones (tripletes) tiene. */
    fun codones(): Int = longitud() / 3

    fun codonesTexto(): String = "%,d".format(codones())

    fun empiezaConATG(): Boolean = texto.startsWith("ATG")

    fun terminaEnStop(): Boolean = texto.takeLast(3) in setOf("TAA", "TAG", "TGA")

    /** Resumen de inicio y fin: "ATG ✓ · Stop ✓". */
    fun inicioFinTexto(): String =
        (if (empiezaConATG()) "ATG ✓" else "ATG ✗") + " · " +
                (if (terminaEnStop()) "Stop ✓" else "Stop ✗")

    fun idTexto(): String = idTranscrito ?: "Sin dato"

    /**
     * Las bases en bloques de 10, como en GenBank, para que se lean mejor.
     * `limite` = cuántas bases mostrar (null = todas).
     */
    fun basesEnBloques(limite: Int? = null): String {
        val parte = if (limite != null) texto.take(limite) else texto
        return parte.chunked(10).joinToString(" ")
    }
}
