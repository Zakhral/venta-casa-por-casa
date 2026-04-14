package com.example.ventas

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ventas.databinding.ActivityReportesBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReportesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReportesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGenerarReporte.setOnClickListener {
            generarReporte()
        }

        binding.btnVolverMenu.setOnClickListener {
            finish()
        }
    }

    private fun generarReporte() {
        val inicio = binding.etFechaInicio.text.toString().trim()
        val fin = binding.etFechaFin.text.toString().trim()

        if (inicio.isEmpty() || fin.isEmpty()) {
            Toast.makeText(this, "Ingresa fecha inicio y fecha fin", Toast.LENGTH_SHORT).show()
            return
        }

        mostrarCarga(true)

        RetrofitClient.apiService.obtenerReportePorPeriodo(inicio, fin)
            .enqueue(object : Callback<ReporteResponse> {
                override fun onResponse(call: Call<ReporteResponse>, response: Response<ReporteResponse>) {
                    mostrarCarga(false)

                    if (response.isSuccessful && response.body() != null) {
                        val respuesta = response.body()!!

                        if (respuesta.success && respuesta.data != null) {
                            binding.tvCantidadVentas.text =
                                "Cantidad de ventas: ${respuesta.data.cantidad_ventas}"

                            binding.tvTotalVendido.text =
                                "Total vendido: $${respuesta.data.total_vendido}"

                            val detalle = StringBuilder()

                            if (respuesta.data.ventas.isEmpty()) {
                                detalle.append("No hay ventas en ese periodo")
                            } else {
                                for (venta in respuesta.data.ventas) {
                                    detalle.append("ID Venta: ${venta.id}\n")
                                    detalle.append("Cliente ID: ${venta.cliente_id}\n")
                                    detalle.append("Producto ID: ${venta.producto_id}\n")
                                    detalle.append("Cantidad: ${venta.cantidad}\n")
                                    detalle.append("Precio unitario: ${venta.precio_unitario}\n")
                                    detalle.append("Total: ${venta.total}\n")
                                    detalle.append("Fecha: ${venta.fecha}\n")
                                    detalle.append("--------------------------\n")
                                }
                            }

                            binding.tvDetalleVentas.text = detalle.toString()

                            Toast.makeText(
                                this@ReportesActivity,
                                respuesta.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                this@ReportesActivity,
                                respuesta.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@ReportesActivity,
                            "Error al generar reporte",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ReporteResponse>, t: Throwable) {
                    mostrarCarga(false)
                    Toast.makeText(
                        this@ReportesActivity,
                        "Error de conexión: ${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    private fun mostrarCarga(mostrar: Boolean) {
        binding.progressBarReportes.visibility = if (mostrar) View.VISIBLE else View.GONE
    }
}