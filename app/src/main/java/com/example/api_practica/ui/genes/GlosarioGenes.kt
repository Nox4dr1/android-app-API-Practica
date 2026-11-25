package com.example.api_practica.ui.genes

/**
 * Explicación corta de cada dato que muestra la pantalla de genes.
 * Se muestran al tocar un recuadro (ver DatoGen en GenesScreen).
 */
internal object GlosarioGenes {

    // ---------- Ficha del gen ----------
    const val CROMOSOMA =
        "Cromosoma donde se encuentra el gen. Los humanos tenemos 22 pares numerados, más los cromosomas sexuales X e Y."
    const val HEBRA =
        "En cuál de las dos cadenas del ADN está el gen: +1 es la cadena directa y −1 la complementaria (se lee en sentido inverso)."
    const val INICIO =
        "Posición, en pares de bases, donde empieza el gen dentro del cromosoma."
    const val FIN =
        "Posición, en pares de bases, donde termina el gen dentro del cromosoma."
    const val LONGITUD_GEN =
        "Tamaño del gen en el genoma, con exones e intrones. 1 kb equivale a 1 000 pares de bases."
    const val ENSAMBLAJE =
        "Versión del genoma de referencia en la que están medidas las posiciones. GRCh38 es la versión actual del genoma humano; en otra versión las posiciones cambian."

    // ---------- Secuencia (CDS) ----------
    const val LONGITUD_CDS =
        "Cantidad de bases de la secuencia codificante: solo la parte de los exones que se traduce a proteína, sin intrones."
    const val CONTENIDO_GC =
        "Porcentaje de bases G y C. Un GC alto hace al ADN más estable; en cebadores se busca entre 40 y 60 %."
    const val CODONES =
        "Grupos de 3 bases. Cada codón indica un aminoácido de la proteína; el último es el codón de término."
    const val INICIO_FIN =
        "Comprueba que la secuencia empiece con el codón de inicio ATG y termine en un codón de término (TAA, TAG o TGA)."


    // ---------- Ayuda para buscar ----------

    /**
     * Qué sistema de nombres oficial usar para el símbolo, según la especie escrita.
     * Si la especie está vacía, se asume humano (la búsqueda por defecto).
     */
    fun ayudaSimbolo(especie: String): String =
        when (especie.trim().lowercase().replace(" ", "_")) {
            "", "homo_sapiens" ->
                "Símbolo oficial HGNC (humano), en mayúsculas. Ej.: BRCA2, TP53, CFTR"
            "mus_musculus" ->
                "Símbolo oficial MGI (ratón), solo la primera letra en mayúscula. Ej.: Brca2, Trp53"
            "drosophila_melanogaster" ->
                "Símbolo de FlyBase (mosca de la fruta). Ej.: w, Adh"
            "danio_rerio" ->
                "Símbolo de ZFIN (pez cebra), en minúsculas. Ej.: brca2, tp53"
            else ->
                "Usa el símbolo oficial del gen en esa especie."
        }

}