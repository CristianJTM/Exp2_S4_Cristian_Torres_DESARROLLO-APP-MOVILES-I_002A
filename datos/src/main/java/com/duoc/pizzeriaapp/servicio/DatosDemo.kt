package com.duoc.pizzeriaapp.servicio

import com.duoc.pizzeriaapp.modelo.Bebida
import com.duoc.pizzeriaapp.modelo.Cliente
import com.duoc.pizzeriaapp.modelo.ComboPromocionable
import com.duoc.pizzeriaapp.modelo.ItemPedido
import com.duoc.pizzeriaapp.modelo.Pizza
import com.duoc.pizzeriaapp.modelo.PizzaPromocionable
import com.duoc.pizzeriaapp.modelo.Repartidor

// Datos de demostracion separados de la UI para mantener MainActivity enfocado en Compose.
object DatosDemo {

    // Instancia unica compartida: el ViewModel la consume, no la recrea.
    val instancia: Pizzeria by lazy { crearPizzeria() }

    fun crearPizzeria(): Pizzeria {
        val catalogo: Catalogo = Catalogo()

        catalogo.agregar(
            PizzaPromocionable(
                "Pepperoni Familiar",
                12990.0,
                mapOf("Masa" to 1, "Salsa" to 1, "Queso" to 2, "Pepperoni" to 1)
            )
        )
        catalogo.agregar(
            Pizza(
                "Margarita Familiar",
                10990.0,
                mapOf("Masa" to 1, "Salsa" to 1, "Queso" to 2, "Tomate" to 1)
            )
        )
        catalogo.agregar(
            ComboPromocionable(
                "Combo Familiar",
                19990.0,
                mapOf("Masa" to 2, "Salsa" to 2, "Queso" to 4, "Pepperoni" to 2)
            )
        )
        catalogo.agregar(Bebida("Bebida Cola 1.5L", 2990.0))

        val inventario: Inventario = Inventario(
            mapOf(
                "Masa" to 40,
                "Salsa" to 40,
                "Queso" to 100,
                "Pepperoni" to 30,
                "Tomate" to 30
            )
        )

        val repartidores: List<Repartidor> = listOf(
            Repartidor("Camila Rojas", "+56 9 1111 1111", "Centro", 1.2),
            Repartidor("Diego Soto", "+56 9 2222 2222", "Centro", 2.5, disponible = false),
            Repartidor("Valentina Díaz", "+56 9 3333 3333", "Norte", 3.0),
            Repartidor("Martín Silva", "+56 9 4444 4444", "Sur", 2.0)
        )

        val pizzeria: Pizzeria = Pizzeria(catalogo.listar(), inventario, repartidores)

        // Clientes de demostración registrados en la fuente única.
        val clientes: List<Cliente> = listOf(
            Cliente("Ana Pérez", "+56 9 5555 1111", "ana.perez@demo.com", "Centro"),
            Cliente("Bruno Díaz", "+56 9 5555 2222", "bruno.diaz@demo.com", "Norte"),
            Cliente("Carla Muñoz", "+56 9 5555 3333", "carla.munoz@demo.com", "Sur"),
            Cliente("Diego Rojas", "+56 9 5555 4444", "diego.rojas@demo.com", "Centro")
        )
        clientes.forEach { pizzeria.registrarCliente(it) }

        // Pedidos de demostración registrados (no sueltos) para que el resumen tenga datos reales.
        pizzeria.registrarPedido(
            clientes[0],
            listOf(ItemPedido(pizzeria.productos.first(), 1)),
            "Pedido demo 1"
        )
        pizzeria.registrarPedido(
            clientes[1],
            listOf(ItemPedido(pizzeria.productos[1], 1)),
            "Pedido demo 2"
        )

        return pizzeria
    }
}
