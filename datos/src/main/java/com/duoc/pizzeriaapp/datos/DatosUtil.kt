package com.duoc.pizzeriaapp.datos

import com.duoc.pizzeriaapp.modelo.ItemPedido
import com.duoc.pizzeriaapp.modelo.Producto
import com.duoc.pizzeriaapp.utils.formatearMonto

// Utilidades que sí dependen del modelo. Viven en :datos (no en :utilidades)
// para evitar una dependencia circular entre módulos Gradle.
fun calcularSubtotal(items: List<ItemPedido>): Double {
    return items.sumOf { it.subtotal }
}

fun Producto.detallarProducto(): String {
    val detalleIngredientes: String = if (ingredientes.isEmpty()) {
        "Sin ingredientes"
    } else {
        ingredientes.entries.joinToString { "${it.key} (${it.value})" }
    }

    return "$nombre - \$${formatearMonto(precio)} | " +
        "Categoría: ${categoria.name.lowercase()} | Ingredientes: $detalleIngredientes"
}
