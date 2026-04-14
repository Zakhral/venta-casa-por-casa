package com.example.ventas

data class VentaRequest(
    val cliente_id: Int,
    val producto_id: Int,
    val cantidad: Int,
    val fecha: String
)