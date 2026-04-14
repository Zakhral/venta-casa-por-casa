package com.example.ventas

data class ReporteResponse(
    val success: Boolean,
    val message: String,
    val data: ReporteData?
)

data class ReporteData(
    val cantidad_ventas: Int,
    val total_vendido: Double,
    val ventas: List<ReporteVenta>
)

data class ReporteVenta(
    val id: Int,
    val cliente_id: Int,
    val producto_id: Int,
    val cantidad: Int,
    val precio_unitario: Double,
    val total: Double,
    val fecha: String
)