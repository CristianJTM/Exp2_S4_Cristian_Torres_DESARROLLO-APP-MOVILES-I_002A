package com.duoc.pizzeriaapp.utils

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidacionesTest {

    @Test
    fun `valida correos`() {
        assertTrue(esCorreoValido("ana.perez@demo.com"))
        assertFalse(esCorreoValido("ana@demo"))
        assertNull(validarCorreo("no-es-correo"))
        assertEquals("ana@demo.com", validarCorreo("  ana@demo.com  "))
    }

    @Test
    fun `normaliza telefonos chilenos`() {
        assertEquals("+56 (9) 5555-1111", normalizarTelefono("955551111"))
        assertEquals("+56 (9) 5555-1111", normalizarTelefono("+56 9 5555 1111"))
        assertNull(normalizarTelefono("123"))
    }

    @Test
    fun `valida texto obligatorio`() {
        assertEquals("Ana", validarTextoObligatorio("  Ana  "))
        assertNull(validarTextoObligatorio("   "))
    }

    @Test
    fun `parsea cantidades y opciones`() {
        assertEquals(5, parsearCantidad(" 5 "))
        assertNull(parsearCantidad("0"))
        assertNull(parsearCantidad("101"))
        assertEquals(2, parsearOpcion("2", 1, 4))
        assertNull(parsearOpcion("9", 1, 4))
    }

    @Test
    fun `parsea si y no`() {
        assertEquals(true, parsearSiNo("Sí"))
        assertEquals(false, parsearSiNo("no"))
        assertNull(parsearSiNo("tal vez"))
    }
}
