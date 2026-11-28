package com.duoc.pizzeriaapp.utils

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CalculosTest {

    @Test
    fun `no aplica descuento en el umbral`() {
        assertFalse(correspondeDescuento(UMBRAL_DESCUENTO_PIZZAS))
        assertEquals(0.0, calcularPorcentajeDescuento(UMBRAL_DESCUENTO_PIZZAS), 0.0)
    }

    @Test
    fun `aplica descuento sobre el umbral`() {
        assertTrue(correspondeDescuento(UMBRAL_DESCUENTO_PIZZAS + 1))
        assertEquals(TASA_DESCUENTO, calcularPorcentajeDescuento(UMBRAL_DESCUENTO_PIZZAS + 1), 0.0)
    }

    @Test
    fun `calcula descuento, impuesto y total`() {
        assertEquals(1000.0, calcularDescuento(10000.0, UMBRAL_DESCUENTO_PIZZAS + 1), 0.01)
        assertEquals(1900.0, calcularImpuesto(10000.0), 0.01)
        assertEquals(10900.0, calcularTotal(10000.0, 1000.0, 1900.0), 0.01)
    }

    @Test
    fun `descuento promocional solo si esta en promocion`() {
        assertEquals(500.0, calcularDescuentoPromocional(10000.0, true), 0.01)
        assertEquals(0.0, calcularDescuentoPromocional(10000.0, false), 0.01)
    }
}
