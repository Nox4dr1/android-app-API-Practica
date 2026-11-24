# API-Practica

App Android de biología que consume **4 APIs públicas**: especies, observaciones, artículos científicos y genes.
Proyecto de práctica para aprender a consumir APIs REST desde Android con Kotlin y Jetpack Compose.

## APIs utilizadas

| # | API | Datos | Ejemplo de endpoint |
|---|-----|-------|---------------------|
| 1 | [GBIF](https://www.gbif.org/developer/summary) | Taxonomía y datos de especies | `https://api.gbif.org/v1/species/search?q=Puma%20concolor` |
| 2 | [iNaturalist](https://api.inaturalist.org/v1/docs/) | Taxones, fotos y observaciones | `https://api.inaturalist.org/v1/taxa?q=Puma%20concolor` |
| 3 | [Crossref](https://www.crossref.org/documentation/retrieve-metadata/rest-api/) | Artículos científicos | `https://api.crossref.org/works?query=puma+concolor&rows=5` |
| 4 | [Ensembl](https://rest.ensembl.org/) | Genes / ADN | `https://rest.ensembl.org/lookup/symbol/homo_sapiens/BRCA2?content-type=application/json` |

Todas son gratuitas y no requieren API key.

## Estructura del proyecto

```
app/src/main/java/com/example/api_practica/
├── MainActivity.kt
├── model/
│   ├── gbif/          → GbifModels.kt
│   ├── iNaturalist/   → iNaturalistModels.kt
│   ├── crossref/      → CrossrefModels.kt
│   └── ensembl/       → EnsemblModels.kt
└── ui/
    └── theme/         → Color.kt, Theme.kt, Type.kt
```

Cada carpeta dentro de `model/` contiene las `data class` que representan la respuesta JSON de su API.

## Tecnologías

- Kotlin
- Jetpack Compose + Material 3
- minSdk 24 / targetSdk 37

## Ramas

| Rama | Contenido |
|------|-----------|
| `main` | Proyecto base, estructura de carpetas y modelos de GBIF |
| `feature/api-crossref` | Modelos de Crossref (`Autor`, `Articulo`, `CrossrefMessage`, `CrossrefResponse`) |
| `feature/api-ensembl` | Modelos de Ensembl (`Gen`, `Secuencia`) |

## Cómo ejecutar

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/Nox4dr1/android-app-API-Practica.git
   ```
2. Abrir el proyecto en Android Studio.
3. Sincronizar Gradle y ejecutar en un emulador o dispositivo.
