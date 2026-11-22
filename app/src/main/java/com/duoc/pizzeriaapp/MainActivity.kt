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
import com.duoc.pizzeriaapp.servicio.DatosDemo
import com.duoc.pizzeriaapp.servicio.Promocion
import com.duoc.pizzeriaapp.servicio.Promociones
import com.duoc.pizzeriaapp.ui.theme.PizzeriaAppTheme
import com.duoc.pizzeriaapp.utils.formatearMonto

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PizzeriaAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Datos de demostracion creados una sola vez, provenientes de DatosDemo.
                    val pizzeria = remember { DatosDemo.crearPizzeria() }
                    val pedidoDemo = remember { DatosDemo.crearPedido(pizzeria) }

                    Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                        Greeting(
                            name = "Android"
                        )
                        // Integracion minima con la logica migrada (servicio + modelo + utils).
                        Text(
                            text = "Catálogo: ${pizzeria.productos.size} productos | " +
                                "Promoción activa: ${if (Promocion.estaActiva()) "Sí" else "No"}"
                        )
                        Text(
                            text = "Pedido demo #${pedidoDemo.id} · " +
                                "Total: $${formatearMonto(pedidoDemo.calcularTotal())}"
                        )
                        // Evidencia de @Promocionable leida por reflexion.
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
