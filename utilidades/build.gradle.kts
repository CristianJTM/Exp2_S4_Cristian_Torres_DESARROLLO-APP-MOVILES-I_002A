plugins {
    alias(libs.plugins.kotlin.jvm)
}

// Módulo Kotlin puro (sin Android): constantes, cálculo y formateo independientes del modelo.
kotlin {
    jvmToolchain(11)
}
