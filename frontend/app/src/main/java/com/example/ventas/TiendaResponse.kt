package com.example.ventas

data class TiendaResponse(
    val success: Boolean,
    val message: String,
    val data: TiendaData?
)

data class TiendaData(
    val id: Int,
    val nombre_tienda: String,
    val propietario: String,
    val telefono: String,
    val direccion: String,
    val descripcion: String
)