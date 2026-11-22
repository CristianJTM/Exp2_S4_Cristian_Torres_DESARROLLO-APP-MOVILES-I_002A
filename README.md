# PizzeriaApp

Aplicación Android (Kotlin + Jetpack Compose) de la **Semana 4**: migración del
sistema de pizzería de consola de las semanas 1-3 a un proyecto Android.

La pantalla principal muestra el saludo de bienvenida y un resumen generado por
la lógica de dominio migrada (catálogo, promoción vigente y total de un pedido).

## Estado

Semana 4 completada: el proyecto compila (`./gradlew assembleDebug`) y corre en
el emulador. El repositorio está publicado en GitHub, rama `main`.

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
- Cantidad de productos del catálogo y si la promoción está activa hoy.
- Total de un pedido demo, calculado con descuentos e impuesto.
- Promoción detectada con la anotación `@Promocionable` mediante reflexión.

## Estructura

```
app/src/main/java/com/duoc/pizzeriaapp/
├── MainActivity.kt   # UI Compose; los datos de demo vienen de DatosDemo
├── modelo/           # Cliente, Producto, Pizza, Combo, Bebida, Pedido,
│                     # ItemPedido, EstadoPedido, CategoriaProducto,
│                     # Usuario, Repartidor, Promocionable
├── servicio/         # Catalogo, Inventario, Pizzeria, Promocion, Promociones,
│                     # DatosDemo (datos de demostración)
└── utils/            # Constantes, SecuenciaPedidos, Vigencia, Calculos,
                      # Formato, Validaciones, Reflexion
```

## Notas

- Las clases de dominio no leen ni escriben en consola. Las funciones que antes
  pedían datos por teclado ahora reciben parámetros y devuelven `String`,
  `Boolean` o valores ya normalizados.
- No se agregaron librerías externas: solo las dependencias estándar de Compose.
