package com.example.walkapaw.LogicaActivities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.walkapaw.R
import com.example.walkapaw.databinding.ActivityHomeBinding
import com.example.walkapaw.databinding.FragmentActividadBinding
import com.example.walkapaw.databinding.FragmentCuentaBinding
import com.example.walkapaw.databinding.FragmentSolicitarBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
        showSolicitarTab()
    }

    private fun setupClickListeners() {
        binding.tabSolicitar.setOnClickListener { showSolicitarTab() }
        binding.tabActividad.setOnClickListener { showActividadTab() }
        binding.tabCuenta.setOnClickListener { showCuentaTab() }
    }

    private fun showSolicitarTab() {
        updateTabSelection(0)

        val solicitarBinding = FragmentSolicitarBinding.inflate(
            layoutInflater, binding.frameContent, false
        )

        binding.frameContent.removeAllViews()
        binding.frameContent.addView(solicitarBinding.root)

        solicitarBinding.btnSolicitarPaseo.setOnClickListener {
            startActivity(Intent(this, PaseoActivity::class.java))
        }
    }

    private fun showActividadTab() {
        updateTabSelection(1)

        val actividadBinding = FragmentActividadBinding.inflate(
            layoutInflater, binding.frameContent, false
        )

        binding.frameContent.removeAllViews()
        binding.frameContent.addView(actividadBinding.root)
    }

    private fun showCuentaTab() {
        updateTabSelection(2)

        val cuentaBinding = FragmentCuentaBinding.inflate(
            layoutInflater, binding.frameContent, false
        )

        binding.frameContent.removeAllViews()
        binding.frameContent.addView(cuentaBinding.root)

        cuentaBinding.btnEditarPerfil.setOnClickListener {
            // Falta por implementar
        }

        cuentaBinding.btnConfiguracion.setOnClickListener {
            // Falta por implementar
        }

        cuentaBinding.btnCerrarSesion.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
        }
    }

    private fun updateTabSelection(selectedTab: Int) {
        resetTabColors()
        when (selectedTab) {
            0 -> highlightTab(binding.tabSolicitar)
            1 -> highlightTab(binding.tabActividad)
            2 -> highlightTab(binding.tabCuenta)
        }
    }

    private fun resetTabColors() {
        setTabColor(binding.tabSolicitar, android.R.color.darker_gray)
        setTabColor(binding.tabActividad, android.R.color.darker_gray)
        setTabColor(binding.tabCuenta, android.R.color.darker_gray)
    }

    private fun highlightTab(tab: LinearLayout) {
        setTabColor(tab, R.color.color_primario)
    }

    private fun setTabColor(tab: LinearLayout, colorRes: Int) {
        val imageView = tab.getChildAt(0) as ImageView
        val textView = tab.getChildAt(1) as TextView
        val color = ContextCompat.getColor(this, colorRes)

        imageView.setColorFilter(color)
        textView.setTextColor(color)
    }
}
