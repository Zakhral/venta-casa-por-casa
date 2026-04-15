package com.example.ventas

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ventas.databinding.ActivityVentasBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VentasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVentasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVentasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGuardarVenta.setOnClickListener { guardarVenta() }
        binding.btnConsultarVenta.setOnClickListener { consultarVenta() }
        binding.btnVolverMenu.setOnClickListener { finish() }
    }

    private fun obtenerRequest(): VentaRequest? {
        val clienteIdTexto = binding.etClienteId.text.toString().trim()
        val productoIdTexto = binding.etProductoId.text.toString().trim()
        val cantidadTexto = binding.etCantidadVenta.text.toString().trim()
        val fecha = binding.etFechaVenta.text.toString().trim()

        if (clienteIdTexto.isEmpty() || productoIdTexto.isEmpty() || cantidadTexto.isEmpty() || fecha.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos de la venta", Toast.LENGTH_SHORT).show()
            return null
        }

        return VentaRequest(
            cliente_id = clienteIdTexto.toInt(),
            producto_id = productoIdTexto.toInt(),
            cantidad = cantidadTexto.toInt(),
            fecha = fecha
        )
    }

    private fun obtenerIdVenta(): Int? {
        val idTexto = binding.etIdVenta.text.toString().trim()
        if (idTexto.isEmpty()) {
            Toast.makeText(this, "Ingresa el ID de la venta", Toast.LENGTH_SHORT).show()
            return null
        }
        return idTexto.toInt()
    }

    private fun guardarVenta() {
        val request = obtenerRequest() ?: return
        mostrarCarga(true)

        RetrofitClient.apiService.guardarVenta(request).enqueue(object : Callback<VentaResponse> {
            override fun onResponse(call: Call<VentaResponse>, response: Response<VentaResponse>) {
                mostrarCarga(false)
                if (response.isSuccessful && response.body() != null) {
                    val respuesta = response.body()!!
                    Toast.makeText(this@VentasActivity, respuesta.message, Toast.LENGTH_SHORT).show()

                    if (respuesta.success && respuesta.data != null) {
                        binding.etPrecioUnitarioVenta.setText(respuesta.data.precio_unitario.toString())
                        binding.etTotalVenta.setText(respuesta.data.total.toString())
                        binding.etIdVenta.setText(respuesta.data.id.toString())
                    }
                } else {
                    Toast.makeText(this@VentasActivity, "Error al registrar venta", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<VentaResponse>, t: Throwable) {
                mostrarCarga(false)
                Toast.makeText(this@VentasActivity, "Error de conexión: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun consultarVenta() {
        val id = obtenerIdVenta() ?: return
        mostrarCarga(true)

        RetrofitClient.apiService.obtenerVenta(id).enqueue(object : Callback<VentaResponse> {
            override fun onResponse(call: Call<VentaResponse>, response: Response<VentaResponse>) {
                mostrarCarga(false)
                if (response.isSuccessful && response.body() != null) {
                    val respuesta = response.body()!!
                    if (respuesta.success && respuesta.data != null) {
                        binding.etClienteId.setText(respuesta.data.cliente_id.toString())
                        binding.etProductoId.setText(respuesta.data.producto_id.toString())
                        binding.etCantidadVenta.setText(respuesta.data.cantidad.toString())
                        binding.etFechaVenta.setText(respuesta.data.fecha)
                        binding.etPrecioUnitarioVenta.setText(respuesta.data.precio_unitario.toString())
                        binding.etTotalVenta.setText(respuesta.data.total.toString())
                        Toast.makeText(this@VentasActivity, respuesta.message, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@VentasActivity, respuesta.message, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@VentasActivity, "Error al consultar venta", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<VentaResponse>, t: Throwable) {
                mostrarCarga(false)
                Toast.makeText(this@VentasActivity, "Error de conexión: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun mostrarCarga(mostrar: Boolean) {
        binding.progressBarVentas.visibility = if (mostrar) View.VISIBLE else View.GONE
    }
}