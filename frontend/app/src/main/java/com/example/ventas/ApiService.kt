package com.example.ventas

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("api/auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("api/tienda/guardar")
    fun guardarTienda(@Body request: TiendaRequest): Call<TiendaResponse>

    @GET("api/tienda/obtener")
    fun obtenerTienda(): Call<TiendaResponse>

    @PUT("api/tienda/actualizar")
    fun actualizarTienda(@Body request: TiendaRequest): Call<TiendaResponse>

    @POST("api/productos/guardar")
    fun guardarProducto(@Body request: ProductoRequest): Call<ProductoResponse>

    @GET("api/productos/obtener/{id}")
    fun obtenerProducto(@Path("id") id: Int): Call<ProductoResponse>

    @PUT("api/productos/actualizar/{id}")
    fun actualizarProducto(
        @Path("id") id: Int,
        @Body request: ProductoRequest
    ): Call<ProductoResponse>

    @DELETE("api/productos/eliminar/{id}")
    fun eliminarProducto(@Path("id") id: Int): Call<ProductoResponse>

    @POST("api/clientes/guardar")
    fun guardarCliente(@Body request: ClienteRequest): Call<ClienteResponse>

    @GET("api/clientes/obtener/{id}")
    fun obtenerCliente(@Path("id") id: Int): Call<ClienteResponse>

    @PUT("api/clientes/actualizar/{id}")
    fun actualizarCliente(
        @Path("id") id: Int,
        @Body request: ClienteRequest
    ): Call<ClienteResponse>

    @DELETE("api/clientes/eliminar/{id}")
    fun eliminarCliente(@Path("id") id: Int): Call<ClienteResponse>

    @POST("api/ventas/guardar")
    fun guardarVenta(@Body request: VentaRequest): Call<VentaResponse>

    @GET("api/ventas/obtener/{id}")
    fun obtenerVenta(@Path("id") id: Int): Call<VentaResponse>

    @GET("api/reportes/ventas-periodo")
    fun obtenerReportePorPeriodo(
        @Query("inicio") inicio: String,
        @Query("fin") fin: String
    ): Call<ReporteResponse>
}