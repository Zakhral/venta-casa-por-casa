package com.example.ventas

data class TiendaRequest(
    val nombre_tienda: String,
    val propietario: String,
    val telefono: String,
    val direccion: String,
    val descripcion: String
)