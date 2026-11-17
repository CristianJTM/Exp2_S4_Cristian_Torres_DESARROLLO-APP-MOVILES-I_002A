package com.duoc.pizzeriaapp.modelo

class Repartidor(
    nombre: String,
    contacto: String,
    val zona: String,
    val distanciaKm: Double,
    val disponible: Boolean = true
) : Usuario(nombre, contacto) {

    // Antes imprimía en consola; ahora devuelve el mensaje resultante.
    fun entregarPedido(pedido: Pedido): String {
        return when (pedido.estado) {
            EstadoPedido.EN_CAMINO -> {
                "El pedido #${pedido.id} ya está en camino."
            }
            EstadoPedido.EN_PREPARACION -> {
                "No se puede entregar el pedido #${pedido.id}: primero debe estar Listo."
            }
            EstadoPedido.LISTO -> {
                pedido.asignarRepartidor(this)

                if (pedido.cambiarEstado(EstadoPedido.EN_CAMINO)) {
                    "$nombre está entregando el pedido #${pedido.id}."
                } else {
                    "No se pudo actualizar el estado del pedido #${pedido.id}."
                }
            }
        }
    }

    override fun describir(): String {
        return "Repartidor: $nombre - Teléfono: $contacto - Zona: $zona - Distancia: $distanciaKm km"
    }
}
