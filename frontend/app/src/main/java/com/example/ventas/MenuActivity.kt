package com.example.ventas

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ventas.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usuario = intent.getStringExtra("usuario") ?: "admin"
        binding.tvBienvenida.text = "Bienvenido, $usuario"

        binding.btnTienda.setOnClickListener {
            startActivity(Intent(this, TiendaActivity::class.java))
        }

        binding.btnProductos.setOnClickListener {
            startActivity(Intent(this, ProductosActivity::class.java))
        }

        binding.btnClientes.setOnClickListener {
            startActivity(Intent(this, ClientesActivity::class.java))
        }

        binding.btnVentas.setOnClickListener {
            startActivity(Intent(this, VentasActivity::class.java))
        }

        binding.btnReportes.setOnClickListener {
            startActivity(Intent(this, ReportesActivity::class.java))
        }

        binding.btnCerrarSesion.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}