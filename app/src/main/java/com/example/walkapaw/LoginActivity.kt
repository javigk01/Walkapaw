package com.example.walkapaw

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnVolver = findViewById<Button>(R.id.btnVolver)

        btnLogin.setOnClickListener {
            // Navegar a la pantalla principal después del login exitoso
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish() // Cerrar LoginActivity para que no se pueda volver con back
        }

        btnVolver.setOnClickListener {
            finish() // Volver a MainActivity
        }
    }
}