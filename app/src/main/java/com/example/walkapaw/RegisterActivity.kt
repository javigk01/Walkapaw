package com.example.walkapaw

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)
        val btnVolverRegister = findViewById<Button>(R.id.btnVolverRegister)

        btnRegistrar.setOnClickListener {
            // Navegar a la pantalla principal después del registro exitoso
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish() // Cerrar RegisterActivity para que no se pueda volver con back
        }

        btnVolverRegister.setOnClickListener {
            finish() // Volver a MainActivity
        }
    }
}