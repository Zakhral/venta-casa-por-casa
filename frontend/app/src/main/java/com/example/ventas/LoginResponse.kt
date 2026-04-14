package com.example.ventas

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val data: LoginUserData?
)

data class LoginUserData(
    val id: Int,
    val usuario: String
)