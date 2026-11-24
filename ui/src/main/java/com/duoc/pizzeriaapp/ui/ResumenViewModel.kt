package com.duoc.pizzeriaapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.duoc.pizzeriaapp.modelo.Cliente
import com.duoc.pizzeriaapp.modelo.ItemPedido
import com.duoc.pizzeriaapp.servicio.DatosDemo
import com.duoc.pizzeriaapp.servicio.Pizzeria

// ViewModel del resumen: lee desde la fuente única (DatosDemo), no recrea datos.
class ResumenViewModel : ViewModel() {

    private val pizzeria: Pizzeria = DatosDemo.instancia
    private var contador = 0

    private val _totalClientes = MutableLiveData(0)
    private val _totalPedidos = MutableLiveData(0)
    private val _nombreUltimoCliente = MutableLiveData("Sin clientes")

    val totalClientes: LiveData<Int> = _totalClientes
    val totalPedidos: LiveData<Int> = _totalPedidos
    val nombreUltimoCliente: LiveData<String> = _nombreUltimoCliente

    init {
        actualizar()
    }

    // Relee la Pizzeria y publica; si los datos cambian, la UI se actualiza.
    fun actualizar() {
        _totalClientes.value = pizzeria.listarClientes().size
        _totalPedidos.value = pizzeria.listarPedidos().size
        _nombreUltimoCliente.value = pizzeria.listarClientes().lastOrNull()?.nombre ?: "Sin clientes"
    }

    // Acción de demo: registra un cliente y un pedido, y refresca los LiveData.
    fun registrarClienteYPedidoDemo() {
        contador++
        val cliente = Cliente(
            "Cliente Prueba $contador",
            "+56 9 9000 0000",
            "prueba$contador@demo.com",
            "Centro"
        )
        pizzeria.registrarCliente(cliente)
        pizzeria.registrarPedido(
            cliente,
            listOf(ItemPedido(pizzeria.productos.first(), 1)),
            "Pedido de prueba $contador"
        )
        actualizar()
    }
}
