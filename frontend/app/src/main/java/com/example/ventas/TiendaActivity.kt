package com.example.ventas

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ventas.databinding.ActivityTiendaBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TiendaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTiendaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTiendaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGuardarTienda.setOnClickListener {
            guardarTienda()
        }

        binding.btnConsultarTienda.setOnClickListener {
            consultarTienda()
        }

        binding.btnActualizarTienda.setOnClickListener {
            actualizarTienda()
        }

        binding.btnVolverMenu.setOnClickListener {
            finish()
        }
    }

    private fun obtenerDatosFormulario(): TiendaRequest? {
        val nombreTienda = binding.etNombreTienda.text.toString().trim()
        val propietario = binding.etPropietario.text.toString().trim()
        val telefono = binding.etTelefono.text.toString().trim()
        val direccion = binding.etDireccion.text.toString().trim()
        val descripcion = binding.etDescripcion.text.toString().trim()

        if (nombreTienda.isEmpty() || propietario.isEmpty() || telefono.isEmpty()
            || direccion.isEmpty() || descripcion.isEmpty()
        ) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return null
        }

        return TiendaRequest(
            nombre_tienda = nombreTienda,
            propietario = propietario,
            telefono = telefono,
            direccion = direccion,
            descripcion = descripcion
        )
    }

    private fun guardarTienda() {
        val request = obtenerDatosFormulario() ?: return
        mostrarCarga(true)

        RetrofitClient.apiService.guardarTienda(request).enqueue(object : Callback<TiendaResponse> {
            override fun onResponse(call: Call<TiendaResponse>, response: Response<TiendaResponse>) {
                mostrarCarga(false)

                if (response.isSuccessful && response.body() != null) {
                    val respuesta = response.body()!!
                    Toast.makeText(this@TiendaActivity, respuesta.message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@TiendaActivity, "Error al guardar tienda", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<TiendaResponse>, t: Throwable) {
                mostrarCarga(false)
                Toast.makeText(this@TiendaActivity, "Error de conexión: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun consultarTienda() {
        mostrarCarga(true)

        RetrofitClient.apiService.obtenerTienda().enqueue(object : Callback<TiendaResponse> {
            override fun onResponse(call: Call<TiendaResponse>, response: Response<TiendaResponse>) {
                mostrarCarga(false)

                if (response.isSuccessful && response.body() != null) {
                    val respuesta = response.body()!!

                    if (respuesta.success && respuesta.data != null) {
                        binding.etNombreTienda.setText(respuesta.data.nombre_tienda)
                        binding.etPropietario.setText(respuesta.data.propietario)
                        binding.etTelefono.setText(respuesta.data.telefono)
                        binding.etDireccion.setText(respuesta.data.direccion)
                        binding.etDescripcion.setText(respuesta.data.descripcion)

                        Toast.makeText(this@TiendaActivity, respuesta.message, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@TiendaActivity, respuesta.message, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@TiendaActivity, "Error al consultar tienda", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<TiendaResponse>, t: Throwable) {
                mostrarCarga(false)
                Toast.makeText(this@TiendaActivity, "Error de conexión: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun actualizarTienda() {
        val request = obtenerDatosFormulario() ?: return
        mostrarCarga(true)

        RetrofitClient.apiService.actualizarTienda(request).enqueue(object : Callback<TiendaResponse> {
            override fun onResponse(call: Call<TiendaResponse>, response: Response<TiendaResponse>) {
                mostrarCarga(false)

                if (response.isSuccessful && response.body() != null) {
                    val respuesta = response.body()!!
                    Toast.makeText(this@TiendaActivity, respuesta.message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@TiendaActivity, "Error al actualizar tienda", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<TiendaResponse>, t: Throwable) {
                mostrarCarga(false)
                Toast.makeText(this@TiendaActivity, "Error de conexión: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun mostrarCarga(mostrar: Boolean) {
        binding.progressBarTienda.visibility = if (mostrar) View.VISIBLE else View.GONE
    }
}