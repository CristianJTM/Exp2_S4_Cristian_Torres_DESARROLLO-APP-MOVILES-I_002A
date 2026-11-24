package com.duoc.pizzeriaapp.modelo

class Bebida(
    nombre: String,
    precio: Double
) : Producto(nombre, precio, emptyMap(), CategoriaProducto.BEBIDA)
