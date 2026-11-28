package com.duoc.pizzeriaapp.utils

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class FormatoTest {

    @Test
    fun `formatea montos en pesos chilenos`() {
        assertEquals("12.990", formatearMonto(12990.0))
    }

    @Test
    fun `formatea porcentajes`() {
        assertEquals("10%", formatearPorcentaje(0.10))
    }

    @Test
    fun `formatea telefonos`() {
        assertEquals("+56 (9) 5555-1111", formatearTelefono("56955551111"))
        assertEquals("+56 (9) 5555-1111", formatearTelefono("955551111"))
        assertNull(formatearTelefono("123"))
    }
}
