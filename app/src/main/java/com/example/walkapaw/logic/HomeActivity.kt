package com.example.walkapaw.logic

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.walkapaw.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var b: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(b.root)

        // Cards temporales para navegar pelaos
        b.cardMenu.setOnClickListener { open<MenuActivity>() }
        b.cardSolicitar.setOnClickListener { open<SolicitarActivity>() }
        b.cardActividad.setOnClickListener { open<ActividadActivity>() }
        b.cardCuenta.setOnClickListener { open<CuentaActivity>() }
    }

    private inline fun <reified T : Activity> open() {
        startActivity(
            Intent(this, T::class.java).addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            )
        )
    }
}
