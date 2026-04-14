package com.example.ventas

data class ProductoResponse(
    val success: Boolean,
    val message: String,
    val data: ProductoData?
)

data class ProductoData(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val stock: Int
)