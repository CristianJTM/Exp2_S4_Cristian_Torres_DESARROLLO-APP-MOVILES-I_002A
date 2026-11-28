package com.duoc.pizzeriaapp.datos

import com.duoc.pizzeriaapp.modelo.Bebida
import com.duoc.pizzeriaapp.modelo.ItemPedido
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class DatosUtilTest {

    @Test
    fun `calcula el subtotal de los items`() {
        val bebida = Bebida("Bebida Cola 1.5L", 2990.0)
        val items = listOf(ItemPedido(bebida, 2))
        assertEquals(5980.0, calcularSubtotal(items), 0.01)
    }

    @Test
    fun `detalla un producto sin ingredientes`() {
        val bebida = Bebida("Bebida Cola 1.5L", 2990.0)
        val detalle = bebida.detallarProducto()
        assertTrue(detalle.contains("Bebida Cola 1.5L"))
        assertTrue(detalle.contains("Sin ingredientes"))
    }
}
