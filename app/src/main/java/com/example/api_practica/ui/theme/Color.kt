package com.example.api_practica.ui.theme

import androidx.compose.ui.graphics.Color

// Paleta "Biología a tu alcance" (solo modo claro).
// Azul noche + hélice de ADN (cian → azul) + árbol de la vida (naranja → magenta → morado).

// Base
val AzulNoche = Color(0xFF120E4A)
val FondoLavanda = Color(0xFFF5F3FF)
val LavandaClaro = Color(0xFFE9E5FF)
val TextoPrincipal = Color(0xFF1A1640)
val TextoSecundario = Color(0xFF4B4770)
val TextoSobreNoche = Color(0xFFCFCBF5)
val BordeSuave = Color(0xFFE3DEF7)
val BordeCampo = Color(0xFFB9B3DE)

// Colores vivos (íconos del menú y degradados)
val Cian = Color(0xFF22C3F0)
val AzulVivo = Color(0xFF2F6BFF)
val Naranja = Color(0xFFF7931E)
val Magenta = Color(0xFFE0307A)
val Morado = Color(0xFF7B2CBF)

// Acento de cada API: botones y etiquetas (texto blanco encima se lee bien)
val AcentoEnsembl = Color(0xFFC2185B)
val AcentoGbif = Color(0xFFC45100)
val AcentoCrossref = Color(0xFF2456E6)
val AcentoINaturalist = Color(0xFF7B2CBF)

// Fondo claro de cada API: chips y recuadros suaves (texto del acento encima)
val FondoEnsembl = Color(0xFFFDE7F0)
val FondoGbif = Color(0xFFFFF1E0)
val FondoCrossref = Color(0xFFE6EEFF)
val FondoINaturalist = Color(0xFFF1E8FB)

// Franja de marca debajo de los encabezados
val DegradadoMarca = listOf(Cian, AzulVivo, Morado, Magenta, Naranja)