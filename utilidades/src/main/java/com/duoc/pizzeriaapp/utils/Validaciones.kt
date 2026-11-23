package com.duoc.pizzeriaapp.utils

private val FORMATO_TELEFONO: Regex = Regex(PATRON_TELEFONO)
private val FORMATO_CORREO: Regex = Regex(PATRON_CORREO)

fun esCorreoValido(correo: String): Boolean = FORMATO_CORREO.matches(correo)

fun esTelefonoValido(telefono: String): Boolean {
    return FORMATO_TELEFONO.matches(telefono) && formatearTelefono(telefono) != null
}

// Sin consola: cada función recibe el texto ya escrito en un campo y devuelve
// el valor normalizado, o null si la validación falla.
fun validarTextoObligatorio(texto: String): String? {
    return texto.trim().takeIf { it.isNotEmpty() }
}

fun normalizarTelefono(texto: String): String? {
    val entrada: String = texto.trim()
    if (!esTelefonoValido(entrada)) {
        return null
    }
    return formatearTelefono(entrada)
}

fun validarCorreo(texto: String): String? {
    val correo: String = texto.trim()
    return correo.takeIf { esCorreoValido(it) }
}

fun normalizarNotas(texto: String): String? {
    return texto.trim().takeIf { it.isNotEmpty() }
}

fun parsearCantidad(texto: String): Int? {
    return texto.trim().toIntOrNull()?.takeIf { it in RANGO_CANTIDAD_PERMITIDA }
}

fun parsearOpcion(texto: String, minimo: Int, maximo: Int): Int? {
    return texto.trim().toIntOrNull()?.takeIf { it in minimo..maximo }
}

fun parsearSiNo(texto: String): Boolean? {
    return when (texto.trim().lowercase()) {
        "s", "si", "sí" -> true
        "n", "no" -> false
        else -> null
    }
}
