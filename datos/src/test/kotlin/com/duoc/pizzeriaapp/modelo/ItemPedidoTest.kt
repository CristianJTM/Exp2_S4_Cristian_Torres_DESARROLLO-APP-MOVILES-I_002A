package com.duoc.pizzeriaapp.modelo

import org.junit.Assert.assertEquals
import org.junit.Test

class ItemPedidoTest {

    @Test
    fun `calcula su subtotal`() {
        val producto = Bebida("Bebida Cola 1.5L", 2990.0)
        assertEquals(8970.0, ItemPedido(producto, 3).subtotal, 0.01)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `rechaza cantidad fuera de rango`() {
        ItemPedido(Bebida("Bebida Cola 1.5L", 2990.0), 0)
    }
}
