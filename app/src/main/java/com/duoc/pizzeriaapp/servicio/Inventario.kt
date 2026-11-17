package com.duoc.pizzeriaapp.servicio

import com.duoc.pizzeriaapp.modelo.ItemPedido

class Inventario(disponibilidadInicial: Map<String, Int>) {
    private val disponibilidad: MutableMap<String, Int> = disponibilidadInicial.toMutableMap()

    fun consultar(ingrediente: String): Int = disponibilidad[ingrediente] ?: 0

    fun calcularRequerimientos(ingredientes: List<String>, items: List<ItemPedido>): Map<String, Int> {
        return ingredientes.distinct().associateWith { ingrediente ->
            items.sumOf { item ->
                (item.producto.ingredientes[ingrediente] ?: 0) * item.cantidad
            }
        }
    }

    fun calcularRequerimientos(items: List<ItemPedido>): Map<String, Int> {
        val ingredientes: List<String> = items
            .flatMap { it.producto.ingredientes.keys }
            .distinct()

        return calcularRequerimientos(ingredientes, items)
    }

    fun ingredientesFaltantes(items: List<ItemPedido>): Map<String, Int> {
        return obtenerFaltantes(calcularRequerimientos(items))
    }

    fun actualizarInventario(items: List<ItemPedido>) {
        val requerimientos: Map<String, Int> = calcularRequerimientos(items)
        val faltantes: Map<String, Int> = obtenerFaltantes(requerimientos)

        require(faltantes.isEmpty()) {
            "No se puede actualizar el inventario. Faltan: " +
                faltantes.entries.joinToString { "${it.key} (${it.value})" }
        }

        for ((ingrediente, cantidad) in requerimientos) {
            disponibilidad[ingrediente] = consultar(ingrediente) - cantidad
        }
    }

    private fun obtenerFaltantes(requerimientos: Map<String, Int>): Map<String, Int> {
        return requerimientos
            .filter { (ingrediente, requerido) -> consultar(ingrediente) < requerido }
            .mapValues { (ingrediente, requerido) -> requerido - consultar(ingrediente) }
    }

    // Devuelve false si la cantidad no es válida; ya no imprime en consola.
    fun reponer(ingrediente: String, cantidad: Int): Boolean {
        if (cantidad <= 0) {
            return false
        }

        disponibilidad[ingrediente] = consultar(ingrediente) + cantidad
        return true
    }

    fun ingredientesDisponibles(): Set<String> = disponibilidad.keys

    fun inventarioComoTexto(): String = buildString {
        appendLine("=== INVENTARIO ===")
        for ((ingrediente, cantidad) in disponibilidad) {
            appendLine("- $ingrediente: $cantidad")
        }
    }
}
