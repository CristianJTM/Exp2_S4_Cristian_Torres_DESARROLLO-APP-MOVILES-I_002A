package com.duoc.pizzeriaapp.utils

import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class VigenciaTest {

    private val hoy: LocalDate = LocalDate.of(2026, 11, 27)

    @Test
    fun `calcula el periodo de promocion`() {
        assertEquals(LocalDate.of(2026, 11, 20), inicioPromocion(hoy))
        assertEquals(LocalDate.of(2026, 12, 4), finPromocion(hoy))
    }

    @Test
    fun `evalua si una fecha esta en el periodo`() {
        assertTrue(estaEnPeriodoPromocional(hoy, hoy))
        assertTrue(estaEnPeriodoPromocional(LocalDate.of(2026, 11, 25), hoy))
        assertFalse(estaEnPeriodoPromocional(LocalDate.of(2026, 10, 1), hoy))
    }
}
