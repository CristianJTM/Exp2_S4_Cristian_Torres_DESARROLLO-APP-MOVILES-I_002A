package com.duoc.pizzeriaapp.modelo

import com.duoc.pizzeriaapp.utils.RANGO_CANTIDAD_PERMITIDA

data class ItemPedido(
    val producto: Producto,
    val cantidad: Int
) {
    init {
        require(cantidad in RANGO_CANTIDAD_PERMITIDA) {
            "La cantidad debe estar entre ${RANGO_CANTIDAD_PERMITIDA.first} y ${RANGO_CANTIDAD_PERMITIDA.last}."
        }
    }

    val subtotal: Double
        get() = producto.precio * cantidad
}
