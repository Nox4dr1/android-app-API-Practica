package com.example.api_practica.model.gbif

// Aquí van las data class de la API
data class Especie(
    val key: Long,
    val scientificName: String,
    val kingdom: String?,
    val phylum: String?,
    val order: String?,
    val family: String?,
    val genus: String?
)

data class GbifResponse(
    val count: Int,
    val results: List<Especie>
)

