package com.example.ventas

data class ClienteResponse(
    val success: Boolean,
    val message: String,
    val data: ClienteData?
)

data class ClienteData(
    val id: Int,
    val nombre: String,
    val telefono: String,
    val direccion: String
)