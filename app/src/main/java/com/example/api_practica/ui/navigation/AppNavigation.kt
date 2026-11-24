package com.example.api_practica.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.api_practica.ui.articulos.ArticulosScreen
import com.example.api_practica.ui.especies.EspeciesScreen
import com.example.api_practica.ui.genes.GenesScreen
import com.example.api_practica.ui.menu.MenuScreen
import com.example.api_practica.ui.taxones.TaxonesScreen

private const val RUTA_MENU = "menu"
private const val RUTA_ESPECIES = "especies"
private const val RUTA_ARTICULOS = "articulos"
private const val RUTA_GENES = "genes"
private const val RUTA_TAXONES = "taxones"

/**
 * Grafo de navegación: "menu" (inicio) -> cada una de las 4 pantallas.
 * Cada pantalla recibe solo `onVolver`, así ninguna depende de las otras.
 */
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val volver: () -> Unit = { navController.popBackStack() }

    NavHost(navController = navController, startDestination = RUTA_MENU) {
        composable(RUTA_MENU) {
            MenuScreen(
                onIrAEspecies = { navController.navigate(RUTA_ESPECIES) },
                onIrAArticulos = { navController.navigate(RUTA_ARTICULOS) },
                onIrAGenes = { navController.navigate(RUTA_GENES) },
                onIrATaxones = { navController.navigate(RUTA_TAXONES) }
            )
        }
        composable(RUTA_ESPECIES) { EspeciesScreen(onVolver = volver) }
        composable(RUTA_ARTICULOS) { ArticulosScreen(onVolver = volver) }
        composable(RUTA_GENES) { GenesScreen(onVolver = volver) }
        composable(RUTA_TAXONES) { TaxonesScreen(onVolver = volver) }
    }
}