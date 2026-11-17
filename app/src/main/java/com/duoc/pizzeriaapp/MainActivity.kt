package com.duoc.pizzeriaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.duoc.pizzeriaapp.modelo.Bebida
import com.duoc.pizzeriaapp.modelo.Cliente
import com.duoc.pizzeriaapp.modelo.ComboPromocionable
import com.duoc.pizzeriaapp.modelo.ItemPedido
import com.duoc.pizzeriaapp.modelo.Pedido
import com.duoc.pizzeriaapp.modelo.Pizza
import com.duoc.pizzeriaapp.modelo.PizzaPromocionable
import com.duoc.pizzeriaapp.modelo.Repartidor
import com.duoc.pizzeriaapp.servicio.Catalogo
import com.duoc.pizzeriaapp.servicio.Inventario
import com.duoc.pizzeriaapp.servicio.Pizzeria
import com.duoc.pizzeriaapp.servicio.Promocion
import com.duoc.pizzeriaapp.servicio.Promociones
import com.duoc.pizzeriaapp.ui.theme.PizzeriaAppTheme
import com.duoc.pizzeriaapp.utils.SecuenciaPedidos
import com.duoc.pizzeriaapp.utils.formatearMonto

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PizzeriaAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Datos de demostración creados una sola vez por recomposición evitada.
                    val pizzeria = remember { crearPizzeriaDemo() }
                    val pedidoDemo = remember { crearPedidoDemo(pizzeria) }

                    Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                        Greeting(
                            name = "Android"
                        )
                        // Integración mínima con la lógica migrada (servicio + modelo + utils).
                        Text(
                            text = "Catálogo: ${pizzeria.productos.size} productos | " +
                                "Promoción activa: ${if (Promocion.estaActiva()) "Sí" else "No"}"
                        )
                        Text(
                            text = "Pedido demo #${pedidoDemo.id} · " +
                                "Total: $${formatearMonto(pedidoDemo.calcularTotal())}"
                        )
                        // Evidencia de @Promocionable leída por reflexión.
                        Text(
                            text = "Promoción: ${Promociones.descripcion(pizzeria.productos.first())}"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Bienvenidos a la mejor pizzería de la ciudad",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PizzeriaAppTheme {
        Greeting("Android")
    }
}

// Adaptación de crearPizzeria() del proyecto de consola, sin salidas por consola.
private fun crearPizzeriaDemo(): Pizzeria {
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

// Pedido de demostración construido directamente para no alterar el inventario.
private fun crearPedidoDemo(pizzeria: Pizzeria): Pedido {
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
