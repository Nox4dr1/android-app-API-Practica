# 🧬 Biología a tu alcance

Aplicación móvil desarrollada en Android con Kotlin y Jetpack Compose que permite investigar especies, observaciones en la naturaleza, artículos científicos y genes consultando 4 APIs públicas en tiempo real.

---

## APIs

La aplicación consume las siguientes APIs REST gratuitas (sin necesidad de API key):

| # | API | Descripción | Endpoint de ejemplo |
|---|-----|-------------|---------------------|
| 1 | **GBIF** | Taxonomía y clasificación de especies | `https://api.gbif.org/v1/species/search?q=Puma%20concolor` |
| 2 | **iNaturalist** | Taxones, fotos y observaciones de flora y fauna | `https://api.inaturalist.org/v1/taxa?q=Puma%20concolor` |
| 3 | **Crossref** | Publicaciones científicas y artículos académicos | `https://api.crossref.org/works?query=puma+concolor&rows=5` |
| 4 | **Ensembl** | Ubicación genómica, cromosomas y secuencias de ADN | `https://rest.ensembl.org/lookup/symbol/homo_sapiens/BRCA2?content-type=application/json` |

---

## Paleta de colores

La identidad visual está inspirada en la biología, el ADN y la naturaleza, utilizando una paleta moderna en modo claro:

| Color | Hex / Valor | Uso principal |
|-------|------------|---------------|
| **Azul Noche** | `#120E4A` | Encabezados principales y barras de navegación |
| **Fondo Lavanda** | `#F5F3FF` | Fondo general de las pantallas |
| **Cian** | `#22C3F0` | Acentos de hélice de ADN y degradados |
| **Azul Vivo** | `#2F6BFF` | Color corporativo y sección de artículos (Crossref) |
| **Naranja** | `#F7931E` | Sección de especies (GBIF) |
| **Magenta** | `#E0307A` | Sección de genes (Ensembl) |
| **Morado** | `#7B2CBF` | Sección de fotos (iNaturalist) |
| **Texto Secundario** | `#4B4770` | Subtítulos y metadatos |

---

## Arquitectura MVVM

La aplicación implementa el patrón **MVVM (Model-View-ViewModel)** con una estricta separación de responsabilidades:

| Capa | Archivos clave | Responsabilidad |
|------|----------------|-----------------|
| **Vista (UI)** | `MenuScreen.kt`, `GenesScreen.kt`, `EspeciesScreen.kt`, `ArticulosScreen.kt`, `TaxonesScreen.kt`, `EncabezadoPantalla.kt` | Interfaz declarativa en Jetpack Compose que observa el estado y delega eventos de usuario. |
| **ViewModel** | `GenesViewModel.kt` | Gestiona el estado reactivo (`StateFlow`), ejecuta corrutinas fuera del hilo principal y procesa la lógica de presentación. |
| **Repositorio** | `GenRepository.kt` | Abstrae la fuente de datos, obtiene la información desde la API (red). |
| **Red (API Service)** | `EnsemblApiService.kt`, `GbifApiService.kt`, `CrossrefApiService.kt`, `INaturalistApiService.kt`, `RetrofitClient.kt` | Define las interfaces Retrofit para realizar peticiones HTTP a las APIs externas. |
| **Modelo** | `Gen.kt`, clases en `model/` | Estructuras de datos (`data class`) con Gson y métodos de encapsulamiento para formatear la información. |

---

## Principios SOLID

El proyecto aplica los principios SOLID a lo largo de su diseño y desarrollo:

| Principio | Aplicación en el proyecto |
|---|---|
| **S** (Responsabilidad Única) | Cada componente tiene un único propósito: `Gen` formatea sus datos, `GenesScreen` dibuja la UI, `GenesViewModel` administra el estado, y `GenRepositoryRemoto` gestiona la red. |
| **O** (Abierto / Cerrado) | Los componentes visuales como `EncabezadoPantalla` y `PantallaEnConstruccion` están abiertos a la reutilización sin necesidad de modificar su código interno. |
| **L** (Sustitución de Liskov) | `GenRepositoryRemoto` implementa la interfaz `GenRepository`, pudiendo ser sustituido por un repositorio simulado (Mock/Local) sin afectar al ViewModel. |
| **I** (Segregación de Interfaces) | Cada API cuenta con su propio servicio de red específico (`EnsemblApiService`, `GbifApiService`, etc.), evitando depender de métodos innecesarios. |
| **D** (Inversión de Dependencias) | El `GenesViewModel` depende de la abstracción `GenRepository` (interfaz) en lugar de una implementación concreta de red. |

---

## Clases principales

| Clase | Tipo | Capa | Descripción |
|---|---|---|---|
| `GenesScreen` | Composable | Vista | Pantalla principal de búsqueda de genes y visualización de fichas de ADN. |
| `GenesViewModel` | ViewModel | ViewModel | Administra el `GenesUiState` y maneja las corrutinas para la consulta de genes. |
| `GenRepository` | Interface | Repositorio | Contrato abstracto para las operaciones de obtención de genes. |
| `GenRepositoryRemoto` | Class | Repositorio | Implementación concreta de `GenRepository` conectada a Retrofit (ubicada en `GenRepository.kt`). |
| `EnsemblApiService` | Interface | Red | Interfaz Retrofit con los endpoints HTTP de la API de Ensembl. |
| `Gen` | Data Class | Modelo | Modelo de datos que representa un gen y sus funciones de formateo (`tipoTexto()`, `longitudKb()`, etc.). |

---

## Cómo ejecutar

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/Nox4dr1/android-app-API-Practica.git
   ```
2. Abrir el proyecto en **Android Studio**.
3. Sincronizar las dependencias de **Gradle**.
4. Ejecutar la aplicación en un emulador Android o dispositivo físico (compatible con SDK 24 a 37).
