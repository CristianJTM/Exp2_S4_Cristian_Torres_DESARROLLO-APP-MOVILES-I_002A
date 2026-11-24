# PizzeriaApp

Aplicación Android (Kotlin + Jetpack Compose) de la **Semana 5**. Parte de la
migración del sistema de pizzería de consola (semanas 1-3) a Android (semana 4)
y ahora está separada en **módulos Gradle reales**.

La pantalla principal muestra el saludo de bienvenida y un **resumen dinámico**
generado por la lógica de dominio: total de clientes registrados, total de
pedidos realizados y nombre del último cliente registrado.

## Estado

Semana 5 completada: modularización en `:app`, `:datos`, `:ui` y `:utilidades`.
El proyecto compila (`./gradlew assembleDebug`) y corre en el emulador. El
repositorio está publicado en GitHub; esta entrega vive en la rama `semana5`
(`main` conserva la entrega de la semana 4).

## Requisitos

- Android Studio con AGP 9.5.1, Gradle 9.6 y Kotlin 2.2.10.
- JDK 11 o superior.
- Emulador o dispositivo con Android 7.0 (API 24) o superior.
  Se recomienda **API 26+** porque el dominio usa `java.time.LocalDate`.

## Cómo ejecutar

1. Abrir el proyecto en Android Studio.
2. Esperar la sincronización de Gradle.
3. Seleccionar el módulo `app` y presionar **Run** en un emulador o dispositivo.

Por línea de comandos:

```
./gradlew assembleDebug     # genera app/build/outputs/apk/debug/app-debug.apk
./gradlew installDebug      # instala en el emulador o dispositivo conectado
```

## Qué muestra la pantalla

- **Bienvenidos a la mejor pizzería de la ciudad** (función `Greeting`).
- **Clientes registrados**: total de clientes en la pizzería.
- **Pedidos realizados**: total de pedidos registrados.
- **Último cliente**: nombre del último cliente registrado.

El resumen lo arma un `ViewModel` de `:ui` que expone `LiveData`; la pantalla los
observa con `observeAsState`, así que se actualiza sola si los datos cambian.
La fuente de datos de demostración es `DatosDemo`, que registra 4 clientes y 2
pedidos demo.

## Estructura modular

```
PizzeriaApp/
├── app/          # :app — host mínimo: MainActivity (tema + pantalla)
├── utilidades/   # :utilidades — Kotlin/JVM puro
├── datos/        # :datos — Kotlin/JVM puro
└── ui/           # :ui — Android library con Compose
```

Cadena de dependencias entre módulos:

```
:app  →  :ui  →  :datos  →  :utilidades
```

### `:utilidades` (Kotlin puro, sin Android)

`utilidades/src/main/java/com/duoc/pizzeriaapp/utils/`

- `Constantes.kt`, `SecuenciaPedidos.kt`, `Vigencia.kt`, `Calculos.kt`,
  `Formato.kt`, `Validaciones.kt`, `Reflexion.kt`.
- Solo lógica que **no depende del modelo**: constantes, cálculos, formateo,
  validaciones y vigencia.

### `:datos` (Kotlin puro, sin Android)

`datos/src/main/java/com/duoc/pizzeriaapp/`

- `modelo/` — Cliente, Producto, Pizza, Combo, Bebida, Pedido, ItemPedido,
  EstadoPedido, CategoriaProducto, Usuario, Repartidor, Promocionable.
- `servicio/` — Catalogo, Inventario, Pizzeria, Promocion, Promociones,
  DatosDemo (fuente única de los datos de demostración).
- `datos/DatosUtil.kt` — `calcularSubtotal` y `Producto.detallarProducto`,
  utilidades que sí dependen del modelo. Se movieron aquí para evitar una
  dependencia circular entre `:utilidades` y `:datos`.

### `:ui` (Android library con Compose)

`ui/src/main/java/com/duoc/pizzeriaapp/ui/`

- `ResumenViewModel.kt` — expone `totalClientes`, `totalPedidos` y
  `nombreUltimoCliente` como `LiveData`, leídos desde `DatosDemo`.
- `ResumenScreen.kt` — pantalla Compose que observa el `ViewModel`
  (`observeAsState`) e incluye el saludo `Greeting`.
- `theme/` — Color, Theme y Type de Compose.

Namespace propio del módulo: `com.duoc.pizzeriaapp.ui`.

### `:app` (aplicación Android)

`app/src/main/java/com/duoc/pizzeriaapp/`

- `MainActivity.kt` — solo host: aplica el tema y monta `ResumenScreen` de
  `:ui`. No contiene lógica de negocio ni crea datos.

## Dependencias

Las versiones se centralizan en `gradle/libs.versions.toml`. Además de lo de la
semana 4 se agregaron: `lifecycle-viewmodel-ktx`, `lifecycle-livedata-ktx`,
`lifecycle-viewmodel-compose`, `androidx.compose.runtime:runtime-livedata` y
`kotlinx-coroutines` (core/android).

## Notas

- Las clases de dominio no leen ni escriben en consola. Las funciones que antes
  pedían datos por teclado ahora reciben parámetros y devuelven `String`,
  `Boolean` o valores ya normalizados.
- `:utilidades` y `:datos` son módulos Kotlin puros (JVM) porque modelo,
  servicio y utils no dependen de `android.*` ni de Compose; solo `:ui` y
  `:app` son módulos Android.
- `DatosDemo` sigue siendo la fuente única de los datos de demostración. El
  `ViewModel` de `:ui` la consume; no recrea datos.
- Se requiere API 26+ en ejecución por el uso de `java.time.LocalDate`.
