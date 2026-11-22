package com.duoc.pizzeriaapp.servicio

import com.duoc.pizzeriaapp.modelo.Bebida
import com.duoc.pizzeriaapp.modelo.Cliente
import com.duoc.pizzeriaapp.modelo.ComboPromocionable
import com.duoc.pizzeriaapp.modelo.ItemPedido
import com.duoc.pizzeriaapp.modelo.Pedido
import com.duoc.pizzeriaapp.modelo.Pizza
import com.duoc.pizzeriaapp.modelo.PizzaPromocionable
import com.duoc.pizzeriaapp.modelo.Repartidor
import com.duoc.pizzeriaapp.utils.SecuenciaPedidos

// Datos de demostracion separados de la UI para mantener MainActivity enfocado en Compose.
object DatosDemo {

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

        return Pizzeria(catalogo.listar(), inventario, repartidores)
    }

    // Pedido de demostracion construido directamente para no alterar el inventario.
    fun crearPedido(pizzeria: Pizzeria): Pedido {
        val cliente: Cliente = Cliente(
            "Cliente Demo",
            "+56 9 1234 5678",
            "cliente@demo.com",
            "Centro"
        )

        return Pedido(
            id = SecuenciaPedidos.siguiente(),
            cliente = cliente,
            itemsIniciales = listOf(ItemPedido(pizzeria.productos.first(), 2)),
            notas = "Pedido de demostración"
        )
    }
}
