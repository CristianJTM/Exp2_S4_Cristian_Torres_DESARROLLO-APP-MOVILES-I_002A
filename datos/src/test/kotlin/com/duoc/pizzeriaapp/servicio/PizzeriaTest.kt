package com.duoc.pizzeriaapp.servicio

import com.duoc.pizzeriaapp.modelo.Bebida
import com.duoc.pizzeriaapp.modelo.Cliente
import com.duoc.pizzeriaapp.modelo.ItemPedido
import com.duoc.pizzeriaapp.modelo.Pizza
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class PizzeriaTest {

    private fun crearPizzeria(): Pizzeria {
        val catalogo = Catalogo()
        catalogo.agregar(Bebida("Bebida Cola 1.5L", 2990.0))
        return Pizzeria(catalogo.listar(), Inventario(mapOf("Masa" to 1)), emptyList())
    }

    @Test
    fun `registrar cliente no duplica por nombre y correo`() {
        val pizzeria = crearPizzeria()
        pizzeria.registrarCliente(Cliente("Ana Pérez", "+56 9 5555 1111", "ana@demo.com", "Centro"))
        pizzeria.registrarCliente(Cliente("Ana Pérez", "+56 9 5555 1111", "ana@demo.com", "Centro"))
        assertEquals(1, pizzeria.listarClientes().size)
    }

    @Test
    fun `registrar pedido sin items devuelve null`() {
        val pizzeria = crearPizzeria()
        val cliente = Cliente("Ana", "+56 9 5555 1111", "ana@demo.com", "Centro")
        assertNull(pizzeria.registrarPedido(cliente, emptyList(), null))
    }

    @Test
    fun `registrar pedido valido lo agrega`() {
        val pizzeria = crearPizzeria()
        val cliente = Cliente("Ana", "+56 9 5555 1111", "ana@demo.com", "Centro")
        val pedido = pizzeria.registrarPedido(
            cliente,
            listOf(ItemPedido(pizzeria.productos.first(), 1)),
            "demo"
        )
        assertEquals(1, pizzeria.listarPedidos().size)
        assertEquals(pedido, pizzeria.listarPedidos().first())
    }

    @Test
    fun `registrar pedido con inventario insuficiente devuelve null`() {
        val catalogo = Catalogo()
        catalogo.agregar(Pizza("Margarita", 10990.0, mapOf("Masa" to 1)))
        val pizzeria = Pizzeria(catalogo.listar(), Inventario(emptyMap()), emptyList())
        val cliente = Cliente("Ana", "+56 9 5555 1111", "ana@demo.com", "Centro")

        assertNull(
            pizzeria.registrarPedido(
                cliente,
                listOf(ItemPedido(pizzeria.productos.first(), 1)),
                null
            )
        )
        assertEquals(0, pizzeria.listarPedidos().size)
    }
}
