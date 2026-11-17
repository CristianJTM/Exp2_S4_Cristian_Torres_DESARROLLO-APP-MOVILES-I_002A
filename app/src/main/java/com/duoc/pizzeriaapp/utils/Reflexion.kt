package com.duoc.pizzeriaapp.utils

object Reflexion {
    // Devuelve el informe como texto; la UI decide dónde mostrarlo.
    fun listarMiembros(clase: Class<*>): String = buildString {
        appendLine("Clase: ${clase.simpleName}")

        val metodos: MutableList<String> = mutableListOf()
        val campos: MutableList<String> = mutableListOf()
        var actual: Class<*>? = clase

        while (actual != null && actual != Any::class.java) {
            actual.declaredMethods.forEach { metodos.add(it.name) }
            actual.declaredFields.forEach { campos.add(it.name) }
            actual = actual.superclass
        }

        appendLine("Métodos:")
        metodos.distinct().sorted().forEach { appendLine("  - $it") }

        appendLine("Propiedades/campos:")
        campos.distinct().sorted().forEach { appendLine("  - $it") }

        appendLine("Anotaciones:")
        val anotaciones: Array<Annotation> = clase.declaredAnnotations
        if (anotaciones.isEmpty()) {
            appendLine("  - Sin anotaciones")
        } else {
            anotaciones.forEach { appendLine("  - $it") }
        }
    }
}
