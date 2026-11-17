package com.duoc.pizzeriaapp.modelo

// La retención RUNTIME es necesaria: Producto la lee por reflexión.
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Promocionable(val descripcion: String)
