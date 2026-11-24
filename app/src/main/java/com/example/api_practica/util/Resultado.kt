package com.example.api_practica.util

/**
 * Envuelve el resultado de una operación que puede fallar (por ejemplo, una llamada de red).
 * Los repositorios nunca dejan escapar excepciones hacia el ViewModel:
 * siempre devuelven [Exito] con los datos o [Error] con un mensaje para el usuario.
 * Polimorfismo: el ViewModel trata ambos casos con un solo `when`.
 */
sealed class Resultado<out T> {
    data class Exito<out T>(val datos: T) : Resultado<T>()
    data class Error(val mensaje: String) : Resultado<Nothing>()
}