package com.example.walkapaw.logic

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.walkapaw.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {
    private lateinit var b: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(b.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "Menú"

        val opciones = listOf("Viajes", "Pagos", "Promociones", "Ayuda", "Configuración")
        b.listViewOpciones.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, opciones)
        b.listViewOpciones.setOnItemClickListener { _, _, pos, _ ->
            Toast.makeText(this, "Seleccionaste: ${opciones[pos]}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
