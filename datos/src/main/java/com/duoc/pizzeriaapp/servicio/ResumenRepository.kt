package com.duoc.pizzeriaapp.servicio

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// Resumen que consume la UI; se calcula fuera del hilo principal.
data class Resumen(
    val totalClientes: Int,
    val totalPedidos: Int,
    val ultimoCliente: String
)

// Lectura asincrónica del resumen desde la instancia única de DatosDemo.
suspend fun leerResumen(): Resumen = withContext(Dispatchers.Default) {
    val pizzeria: Pizzeria = DatosDemo.instancia
    val clientes = pizzeria.listarClientes()
    Resumen(
        totalClientes = clientes.size,
        totalPedidos = pizzeria.listarPedidos().size,
        ultimoCliente = clientes.lastOrNull()?.nombre ?: "Sin clientes"
    )
}
