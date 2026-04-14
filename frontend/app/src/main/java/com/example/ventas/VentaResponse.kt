package com.example.ventas

data class VentaResponse(
    val success: Boolean,
    val message: String,
    val data: VentaData?
)

data class VentaData(
    val id: Int,
    val cliente_id: Int,
    val producto_id: Int,
    val cantidad: Int,
    val precio_unitario: Double,
    val total: Double,
    val fecha: String
)