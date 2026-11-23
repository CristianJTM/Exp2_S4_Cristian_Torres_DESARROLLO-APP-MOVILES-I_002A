package com.duoc.pizzeriaapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.duoc.pizzeriaapp.servicio.DatosDemo
import com.duoc.pizzeriaapp.servicio.Pizzeria

// ViewModel del resumen: lee desde la fuente única (DatosDemo), no recrea datos.
class ResumenViewModel : ViewModel() {

    private val pizzeria: Pizzeria = DatosDemo.crearPizzeria()

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
}
