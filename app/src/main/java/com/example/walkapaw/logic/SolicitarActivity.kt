package com.example.walkapaw.logic

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.walkapaw.databinding.ActivitySolicitarBinding

class SolicitarActivity : AppCompatActivity() {
    private lateinit var b: ActivitySolicitarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivitySolicitarBinding.inflate(layoutInflater)
        setContentView(b.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "Solicitar"

        b.btnSolicitarPaseo.setOnClickListener {
            startActivity(Intent(this, PaseoActivity::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
