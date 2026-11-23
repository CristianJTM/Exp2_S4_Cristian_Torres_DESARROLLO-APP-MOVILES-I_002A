package com.duoc.pizzeriaapp.servicio

import com.duoc.pizzeriaapp.modelo.Producto

class Catalogo {
    private val productos: MutableList<Producto> = mutableListOf()

    fun agregar(producto: Producto): Boolean {
        if (productos.contains(producto)) {
            return false
        }

        productos.add(producto)
        return true
    }

    fun listar(): List<Producto> = productos.toList()
}
