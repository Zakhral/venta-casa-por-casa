package com.example.ventas

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ventas.databinding.ActivityProductosBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGuardarProducto.setOnClickListener { guardarProducto() }
        binding.btnConsultarProducto.setOnClickListener { consultarProducto() }
        binding.btnActualizarProducto.setOnClickListener { actualizarProducto() }
        binding.btnEliminarProducto.setOnClickListener { eliminarProducto() }
        binding.btnVolverMenu.setOnClickListener { finish() }
    }

    private fun obtenerRequest(): ProductoRequest? {
        val nombre = binding.etNombreProducto.text.toString().trim()
        val descripcion = binding.etDescripcionProducto.text.toString().trim()
        val precioTexto = binding.etPrecioProducto.text.toString().trim()
        val stockTexto = binding.etStockProducto.text.toString().trim()

        if (nombre.isEmpty() || descripcion.isEmpty() || precioTexto.isEmpty() || stockTexto.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos del producto", Toast.LENGTH_SHORT).show()
            return null
        }

        return ProductoRequest(
            nombre = nombre,
            descripcion = descripcion,
            precio = precioTexto.toDouble(),
            stock = stockTexto.toInt()
        )
    }

    private fun obtenerId(): Int? {
        val idTexto = binding.etIdProducto.text.toString().trim()
        if (idTexto.isEmpty()) {
            Toast.makeText(this, "Ingresa el ID del producto", Toast.LENGTH_SHORT).show()
            return null
        }
        return idTexto.toInt()
    }

    private fun guardarProducto() {
        val request = obtenerRequest() ?: return
        mostrarCarga(true)

        RetrofitClient.apiService.guardarProducto(request).enqueue(object : Callback<ProductoResponse> {
            override fun onResponse(call: Call<ProductoResponse>, response: Response<ProductoResponse>) {
                mostrarCarga(false)
                if (response.isSuccessful && response.body() != null) {
                    Toast.makeText(this@ProductosActivity, response.body()!!.message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@ProductosActivity, "Error al guardar producto", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ProductoResponse>, t: Throwable) {
                mostrarCarga(false)
                Toast.makeText(this@ProductosActivity, "Error de conexión: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun consultarProducto() {
        val id = obtenerId() ?: return
        mostrarCarga(true)

        RetrofitClient.apiService.obtenerProducto(id).enqueue(object : Callback<ProductoResponse> {
            override fun onResponse(call: Call<ProductoResponse>, response: Response<ProductoResponse>) {
                mostrarCarga(false)
                if (response.isSuccessful && response.body() != null) {
                    val respuesta = response.body()!!
                    if (respuesta.success && respuesta.data != null) {
                        binding.etNombreProducto.setText(respuesta.data.nombre)
                        binding.etDescripcionProducto.setText(respuesta.data.descripcion)
                        binding.etPrecioProducto.setText(respuesta.data.precio.toString())
                        binding.etStockProducto.setText(respuesta.data.stock.toString())
                        Toast.makeText(this@ProductosActivity, respuesta.message, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@ProductosActivity, respuesta.message, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@ProductosActivity, "Error al consultar producto", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ProductoResponse>, t: Throwable) {
                mostrarCarga(false)
                Toast.makeText(this@ProductosActivity, "Error de conexión: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun actualizarProducto() {
        val id = obtenerId() ?: return
        val request = obtenerRequest() ?: return
        mostrarCarga(true)

        RetrofitClient.apiService.actualizarProducto(id, request).enqueue(object : Callback<ProductoResponse> {
            override fun onResponse(call: Call<ProductoResponse>, response: Response<ProductoResponse>) {
                mostrarCarga(false)
                if (response.isSuccessful && response.body() != null) {
                    Toast.makeText(this@ProductosActivity, response.body()!!.message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@ProductosActivity, "Error al actualizar producto", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ProductoResponse>, t: Throwable) {
                mostrarCarga(false)
                Toast.makeText(this@ProductosActivity, "Error de conexión: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun eliminarProducto() {
        val id = obtenerId() ?: return
        mostrarCarga(true)

        RetrofitClient.apiService.eliminarProducto(id).enqueue(object : Callback<ProductoResponse> {
            override fun onResponse(call: Call<ProductoResponse>, response: Response<ProductoResponse>) {
                mostrarCarga(false)
                if (response.isSuccessful && response.body() != null) {
                    Toast.makeText(this@ProductosActivity, response.body()!!.message, Toast.LENGTH_SHORT).show()
                    limpiarCampos()
                } else {
                    Toast.makeText(this@ProductosActivity, "Error al eliminar producto", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ProductoResponse>, t: Throwable) {
                mostrarCarga(false)
                Toast.makeText(this@ProductosActivity, "Error de conexión: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun limpiarCampos() {
        binding.etIdProducto.setText("")
        binding.etNombreProducto.setText("")
        binding.etDescripcionProducto.setText("")
        binding.etPrecioProducto.setText("")
        binding.etStockProducto.setText("")
    }

    private fun mostrarCarga(mostrar: Boolean) {
        binding.progressBarProductos.visibility = if (mostrar) View.VISIBLE else View.GONE
    }
}