plugins {
    alias(libs.plugins.kotlin.jvm)
}

// Módulo Kotlin puro (sin Android): modelo de dominio + servicios + datos de demostración.
// Ninguna clase de modelo/servicio usa android.* ni coroutines.
kotlin {
    jvmToolchain(11)
}

dependencies {
    implementation(project(":utilidades"))
    implementation(libs.kotlinx.coroutines.core)
}
