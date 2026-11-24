package com.duoc.pizzeriaapp.servicio

import com.duoc.pizzeriaapp.utils.estaEnPeriodoPromocional
import com.duoc.pizzeriaapp.utils.finPromocion
import com.duoc.pizzeriaapp.utils.inicioPromocion
import java.time.LocalDate

object Promocion {
    val inicio: LocalDate
        get() = inicioPromocion()

    val fin: LocalDate
        get() = finPromocion()

    fun estaActiva(fecha: LocalDate = LocalDate.now()): Boolean {
        return estaEnPeriodoPromocional(fecha)
    }
}
