package com.example.api_practica.util


import retrofit2.HttpException
import kotlinx.coroutines.CancellationException
import java.io.IOException

/**
 * Ejecuta una llamada a la API y traduce cualquier error en un [Resultado.Error].
 * SOLID (S y O): el manejo de errores está en un solo lugar y lo reutilizan los 4 repositorios.
 */
suspend fun <T> llamarApi(llamada: suspend () -> T): Resultado<T> =
    try {
        Resultado.Exito(llamada())
    } catch (e: CancellationException) {
        // Si el usuario sale de la pantalla, la corrutina se cancela: NO es un error.
        throw e
    } catch (e: HttpException) {
        // 400/404: la API no encontró lo que se buscó (Ensembl responde 400 si el gen no existe).
        if (e.code() == 400 || e.code() == 404) {
            Resultado.Error("No se encontraron resultados para esa búsqueda.")
        } else {
            Resultado.Error("El servidor respondió con un error (código ${e.code()}).")
        }
    } catch (e: IOException) {
        Resultado.Error("No hay conexión a internet. Revisa tu red e inténtalo de nuevo.")
    } catch (e: Exception) {
        Resultado.Error("Ocurrió un error inesperado: ${e.localizedMessage ?: "desconocido"}")
    }