package com.example.ventas

data class ProductoRequest(
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val stock: Int
)