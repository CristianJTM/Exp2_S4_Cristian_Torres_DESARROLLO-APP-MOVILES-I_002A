package com.duoc.pizzeriaapp.servicio

import com.duoc.pizzeriaapp.modelo.Cliente
import com.duoc.pizzeriaapp.modelo.EstadoPedido
import com.duoc.pizzeriaapp.modelo.ItemPedido
import com.duoc.pizzeriaapp.modelo.Pedido
import com.duoc.pizzeriaapp.modelo.Producto
import com.duoc.pizzeriaapp.modelo.Repartidor
import com.duoc.pizzeriaapp.datos.detallarProducto
import com.duoc.pizzeriaapp.utils.SecuenciaPedidos
import com.duoc.pizzeriaapp.utils.formatearMonto
import java.time.LocalDate

class Pizzeria(
    val productos: List<Producto>,
    val inventario: Inventario,
    val repartidores: List<Repartidor>
) {
    private val pedidos: MutableList<Pedido> = mutableListOf()
    private val clientes: MutableList<Cliente> = mutableListOf()

    fun catalogoComoTexto(): String = buildString {
        appendLine("=== CATÁLOGO DE PIZZAS ===")
        for ((indice, producto) in productos.withIndex()) {
            appendLine("${indice + 1}. ${producto.detallarProducto()}")
        }
    }

    fun registrarCliente(cliente: Cliente): Cliente {
        val existente: Cliente? = clientes.find { it == cliente }

        if (existente != null) {
            return existente
        }

        clientes.add(cliente)
        return cliente
    }

    // Devuelve null cuando no se pudo registrar; el motivo se consulta con verificarDisponibilidad.
    fun registrarPedido(cliente: Cliente, items: List<ItemPedido>, notas: String?): Pedido? {
        if (items.isEmpty()) {
            return null
        }

        val faltantes: Map<String, Int> = verificarDisponibilidad(items)

        return when {
            faltantes.isNotEmpty() -> null
            else -> {
                inventario.actualizarInventario(items)
                crearYRegistrarPedido(cliente, items, notas)
            }
        }
    }

    fun verificarDisponibilidad(items: List<ItemPedido>): Map<String, Int> {
        return inventario.ingredientesFaltantes(items)
    }

    fun describirFaltantes(faltantes: Map<String, Int>): String = buildString {
        appendLine("No hay ingredientes suficientes para preparar el pedido:")
        for ((ingrediente, cantidad) in faltantes) {
            appendLine("- $ingrediente: faltan $cantidad unidades.")
        }
        appendLine("Pasos para reabastecer:")
        appendLine("1. Revisar el ingrediente faltante.")
        appendLine("2. Registrar la compra o entrada al inventario.")
        appendLine("3. Reponerlo desde la opción de inventario.")
        appendLine("4. Verificar nuevamente la disponibilidad.")
    }

    private fun crearYRegistrarPedido(cliente: Cliente, items: List<ItemPedido>, notas: String?): Pedido {
        val fecha: LocalDate = LocalDate.now()
        val pedido: Pedido = Pedido(
            id = SecuenciaPedidos.siguiente(),
            cliente = cliente,
            itemsIniciales = items,
            notas = notas,
            fecha = fecha
        )
        pedidos.add(pedido)
        return pedido
    }

    fun asignarRepartidor(pedido: Pedido): Repartidor? {
        return when (pedido.estado) {
            EstadoPedido.EN_CAMINO -> null
            EstadoPedido.EN_PREPARACION -> null
            EstadoPedido.LISTO -> {
                val repartidor: Repartidor? = repartidores
                    .filter { it.zona == pedido.cliente.zona && it.disponible }
                    .minByOrNull { it.distanciaKm }

                repartidor?.let { it.entregarPedido(pedido) }
                repartidor
            }
        }
    }

    fun listarPedidos(): List<Pedido> = pedidos.toList()

    // Espejo de listarPedidos: expone los clientes registrados para la UI.
    fun listarClientes(): List<Cliente> = clientes.toList()

    fun obtenerPedido(indice: Int): Pedido? = pedidos.getOrNull(indice)

    fun generarReporte(): String {
        val pedidosDelDia: Array<Pedido> = pedidos.toTypedArray()

        if (pedidosDelDia.isEmpty()) {
            return "No hay pedidos registrados hoy."
        }

        val completados: List<Pedido> = pedidosDelDia.filter {
            it.estado == EstadoPedido.LISTO || it.estado == EstadoPedido.EN_CAMINO
        }
        val pendientes: List<Pedido> = pedidosDelDia.filter {
            it.estado == EstadoPedido.EN_PREPARACION
        }

        return buildString {
            appendLine("============================================================")
            appendLine("                 REPORTE DEL DÍA")
            appendLine("============================================================")

            for (pedido in pedidosDelDia) {
                val detallePizzas: String = pedido.items.joinToString { "${it.producto.nombre} x${it.cantidad}" }
                appendLine(
                    "Pedido #${pedido.id} | Cliente: ${pedido.cliente.nombre} | " +
                        "Pizzas: $detallePizzas | " +
                        "Total: \$${formatearMonto(pedido.calcularTotal())} | " +
                        "Estado: ${pedido.estado.descripcion}"
                )
            }

            appendLine("------------------------------------------------------------")
            appendLine("Pedidos listos o en camino: ${completados.size}")
            for (pedido in completados) {
                appendLine("  #${pedido.id} - ${pedido.cliente.nombre} - ${pedido.estado.descripcion}")
            }

            appendLine("Pedidos pendientes: ${pendientes.size}")
            for (pedido in pendientes) {
                appendLine("  #${pedido.id} - ${pedido.cliente.nombre} - ${pedido.estado.descripcion}")
            }
            appendLine("============================================================")
        }
    }
}
