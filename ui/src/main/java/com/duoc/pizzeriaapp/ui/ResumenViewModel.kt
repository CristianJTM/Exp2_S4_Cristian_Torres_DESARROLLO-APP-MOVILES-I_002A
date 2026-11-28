package com.duoc.pizzeriaapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.pizzeriaapp.modelo.Cliente
import com.duoc.pizzeriaapp.modelo.ItemPedido
import com.duoc.pizzeriaapp.servicio.DatosDemo
import com.duoc.pizzeriaapp.servicio.Pizzeria
import com.duoc.pizzeriaapp.servicio.leerResumen
import com.duoc.pizzeriaapp.utils.normalizarTelefono
import com.duoc.pizzeriaapp.utils.parsearCantidad
import com.duoc.pizzeriaapp.utils.parsearOpcion
import com.duoc.pizzeriaapp.utils.validarCorreo
import com.duoc.pizzeriaapp.utils.validarTextoObligatorio
import kotlinx.coroutines.launch

// ViewModel del resumen: lee desde la fuente única (DatosDemo), no recrea datos.
class ResumenViewModel : ViewModel() {

    private val pizzeria: Pizzeria = DatosDemo.instancia

    private val _totalClientes = MutableLiveData(0)
    private val _totalPedidos = MutableLiveData(0)
    private val _nombreUltimoCliente = MutableLiveData("Sin clientes")
    private val _mensaje = MutableLiveData<String?>(null)
    private val _productos = MutableLiveData<List<String>>(emptyList())

    val totalClientes: LiveData<Int> = _totalClientes
    val totalPedidos: LiveData<Int> = _totalPedidos
    val nombreUltimoCliente: LiveData<String> = _nombreUltimoCliente
    val mensaje: LiveData<String?> = _mensaje
    val productos: LiveData<List<String>> = _productos

    init {
        _productos.value = pizzeria.productos.mapIndexed { indice, producto ->
            "${indice + 1}. ${producto.nombre}"
        }
        actualizar()
    }

    // Relee la Pizzeria en segundo plano y publica el resumen.
    fun actualizar() {
        viewModelScope.launch {
            val resumen = leerResumen()
            _totalClientes.postValue(resumen.totalClientes)
            _totalPedidos.postValue(resumen.totalPedidos)
            _nombreUltimoCliente.postValue(resumen.ultimoCliente)
        }
    }

    // Formulario de cliente: valida y registra en la fuente única.
    fun registrarCliente(nombre: String, contacto: String, correo: String, zona: String) {
        val nombreValido = validarTextoObligatorio(nombre)
        val telefonoValido = normalizarTelefono(contacto)
        val correoValido = validarCorreo(correo)
        val zonaValida = validarTextoObligatorio(zona)

        if (nombreValido == null || telefonoValido == null || correoValido == null || zonaValida == null) {
            _mensaje.postValue("Revisa los datos: nombre, teléfono, correo y zona deben ser válidos.")
            return
        }

        viewModelScope.launch {
            val cliente = Cliente(nombreValido, telefonoValido, correoValido, zonaValida)
            pizzeria.registrarCliente(cliente)
            _mensaje.postValue("Cliente registrado: ${cliente.nombre}")
            actualizar()
        }
    }

    // Formulario de pedido: valida producto/cantidad y registra para el último cliente.
    fun registrarPedido(productoTexto: String, cantidadTexto: String) {
        val indice = parsearOpcion(productoTexto, 1, pizzeria.productos.size)?.minus(1)
        val producto = indice?.let { pizzeria.productos.getOrNull(it) }
        val cantidad = parsearCantidad(cantidadTexto)
        val cliente = pizzeria.listarClientes().lastOrNull()

        if (producto == null) {
            _mensaje.postValue("Producto inválido. Usa el número que aparece en el catálogo.")
            return
        }
        if (cantidad == null) {
            _mensaje.postValue("Cantidad inválida (debe estar entre 1 y 100).")
            return
        }
        if (cliente == null) {
            _mensaje.postValue("Primero registra un cliente.")
            return
        }

        viewModelScope.launch {
            val pedido = pizzeria.registrarPedido(
                cliente,
                listOf(ItemPedido(producto, cantidad)),
                "Pedido desde la app"
            )
            if (pedido == null) {
                _mensaje.postValue("No se pudo registrar el pedido: inventario insuficiente.")
            } else {
                _mensaje.postValue("Pedido #${pedido.id} registrado para ${cliente.nombre}.")
                actualizar()
            }
        }
    }
}
