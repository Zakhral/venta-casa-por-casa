package com.example.ventas

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ventas.databinding.ActivityClientesBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClientesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClientesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGuardarCliente.setOnClickListener { guardarCliente() }
        binding.btnConsultarCliente.setOnClickListener { consultarCliente() }
        binding.btnActualizarCliente.setOnClickListener { actualizarCliente() }
        binding.btnEliminarCliente.setOnClickListener { eliminarCliente() }
        binding.btnVolverMenu.setOnClickListener { finish() }
    }

    private fun obtenerRequest(): ClienteRequest? {
        val nombre = binding.etNombreCliente.text.toString().trim()
        val telefono = binding.etTelefonoCliente.text.toString().trim()
        val direccion = binding.etDireccionCliente.text.toString().trim()

        if (nombre.isEmpty() || telefono.isEmpty() || direccion.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos del cliente", Toast.LENGTH_SHORT).show()
            return null
        }

        return ClienteRequest(
            nombre = nombre,
            telefono = telefono,
            direccion = direccion
        )
    }

    private fun obtenerId(): Int? {
        val idTexto = binding.etIdCliente.text.toString().trim()
        if (idTexto.isEmpty()) {
            Toast.makeText(this, "Ingresa el ID del cliente", Toast.LENGTH_SHORT).show()
            return null
        }
        return idTexto.toInt()
    }

    private fun guardarCliente() {
        val request = obtenerRequest() ?: return
        mostrarCarga(true)

        RetrofitClient.apiService.guardarCliente(request).enqueue(object : Callback<ClienteResponse> {
            override fun onResponse(call: Call<ClienteResponse>, response: Response<ClienteResponse>) {
                mostrarCarga(false)
                if (response.isSuccessful && response.body() != null) {
                    Toast.makeText(this@ClientesActivity, response.body()!!.message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@ClientesActivity, "Error al guardar cliente", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ClienteResponse>, t: Throwable) {
                mostrarCarga(false)
                Toast.makeText(this@ClientesActivity, "Error de conexión: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun consultarCliente() {
        val id = obtenerId() ?: return
        mostrarCarga(true)

        RetrofitClient.apiService.obtenerCliente(id).enqueue(object : Callback<ClienteResponse> {
            override fun onResponse(call: Call<ClienteResponse>, response: Response<ClienteResponse>) {
                mostrarCarga(false)
                if (response.isSuccessful && response.body() != null) {
                    val respuesta = response.body()!!
                    if (respuesta.success && respuesta.data != null) {
                        binding.etNombreCliente.setText(respuesta.data.nombre)
                        binding.etTelefonoCliente.setText(respuesta.data.telefono)
                        binding.etDireccionCliente.setText(respuesta.data.direccion)
                        Toast.makeText(this@ClientesActivity, respuesta.message, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@ClientesActivity, respuesta.message, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@ClientesActivity, "Error al consultar cliente", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ClienteResponse>, t: Throwable) {
                mostrarCarga(false)
                Toast.makeText(this@ClientesActivity, "Error de conexión: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun actualizarCliente() {
        val id = obtenerId() ?: return
        val request = obtenerRequest() ?: return
        mostrarCarga(true)

        RetrofitClient.apiService.actualizarCliente(id, request).enqueue(object : Callback<ClienteResponse> {
            override fun onResponse(call: Call<ClienteResponse>, response: Response<ClienteResponse>) {
                mostrarCarga(false)
                if (response.isSuccessful && response.body() != null) {
                    Toast.makeText(this@ClientesActivity, response.body()!!.message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@ClientesActivity, "Error al actualizar cliente", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ClienteResponse>, t: Throwable) {
                mostrarCarga(false)
                Toast.makeText(this@ClientesActivity, "Error de conexión: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun eliminarCliente() {
        val id = obtenerId() ?: return
        mostrarCarga(true)

        RetrofitClient.apiService.eliminarCliente(id).enqueue(object : Callback<ClienteResponse> {
            override fun onResponse(call: Call<ClienteResponse>, response: Response<ClienteResponse>) {
                mostrarCarga(false)
                if (response.isSuccessful && response.body() != null) {
                    Toast.makeText(this@ClientesActivity, response.body()!!.message, Toast.LENGTH_SHORT).show()
                    limpiarCampos()
                } else {
                    Toast.makeText(this@ClientesActivity, "Error al eliminar cliente", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ClienteResponse>, t: Throwable) {
                mostrarCarga(false)
                Toast.makeText(this@ClientesActivity, "Error de conexión: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun limpiarCampos() {
        binding.etIdCliente.setText("")
        binding.etNombreCliente.setText("")
        binding.etTelefonoCliente.setText("")
        binding.etDireccionCliente.setText("")
    }

    private fun mostrarCarga(mostrar: Boolean) {
        binding.progressBarClientes.visibility = if (mostrar) View.VISIBLE else View.GONE
    }
}