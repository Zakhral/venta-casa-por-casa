package com.example.ventas

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class VentasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventas)

        findViewById<Button>(R.id.btnVolverMenu).setOnClickListener {
            finish()
        }
    }
}